package pro.breez.bfsut.util.alert.dialog

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import pro.breez.bfsut.R
import pro.breez.bfsut.adapter.SelectorItemAdapter
import pro.breez.bfsut.databinding.DialogItemSearchBinding
import pro.breez.bfsut.util.ifNotNull
import pro.breez.bfsut.util.ifNull
import pro.breez.bfsut.util.ifTrue

class SearchItemDialog<T>(
    private val valueName: Array<String>,
) :
    DialogFragment() {
    private lateinit var binding: DialogItemSearchBinding

    private var onPositiveClick: View.OnClickListener? = null
    private var onHomeClicked: View.OnClickListener? = null
    private var onNotFoundBtnClick: View.OnClickListener? = null
    private var onKeyChanged: ((String) -> Unit)? = null

    var isHomeBtnGone = false
    var notFoundIcon: Int = R.drawable.ic_not_found
    var notFoundText: String? = null
    var notFoundBtnText: String? = null
    var helperText: String? = null

    private lateinit var adapter: SelectorItemAdapter<T>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogItemSearchBinding.inflate(inflater, container, false)
        requireDialog().window?.setBackgroundDrawableResource(android.R.color.transparent)

        notFoundBtnText.ifNull {
            notFoundBtnText = getString(R.string.create)
        }
        notFoundIcon.ifNull {
            notFoundIcon = R.drawable.ic_search_not_found
        }
        notFoundText.ifNull {
            notFoundText = getString(R.string.need_to_create)
        }
        helperText.ifNull {
            helperText = getString(R.string.check_or_create)
        }

        isHomeBtnGone.ifTrue {
            binding.homeBtn.visibility = View.GONE
        }

        adapter = SelectorItemAdapter(arrayListOf(), valueName, activeBtn = {
            binding.selectBtn.isEnabled = true
        })
        binding.itemRv.adapter = adapter
        binding.selectBtn.setOnClickListener(onPositiveClick)
        binding.homeBtn.setOnClickListener(onHomeClicked)
        binding.helperTv.text = helperText
        onEditTextFilled()
        return binding.root
    }

    private fun onEditTextFilled() {
        var mLastClickTime = 0L
        binding.search.doOnTextChanged { text, _, _, _ ->
            binding.selectBtn.isEnabled = false
            if (text.toString().isNotBlank()) {
                if (SystemClock.elapsedRealtime() - mLastClickTime > 500L) {
                    mLastClickTime = SystemClock.elapsedRealtime()
                    onKeyChanged?.invoke(text.toString())
                }
            } else {
                setDefaultView()
            }
        }
    }

    fun onKeyChanged(block: (String) -> Unit) {
        onKeyChanged = block
    }

    fun updateList(list: List<T>) {
        if (list.isNotEmpty()) {
            adapter.update(list)
            binding.notFoundView.visibility = View.GONE
            binding.itemRv.visibility = View.VISIBLE
            binding.selectBtn.text = "Применить"
            binding.selectBtn.setOnClickListener(onPositiveClick)
        } else {
            binding.notFoundView.visibility = View.VISIBLE
            binding.itemRv.visibility = View.GONE
            binding.img.setImageResource(notFoundIcon)
            binding.helperTv.text = (notFoundText)
            binding.selectBtn.text = notFoundBtnText
            binding.selectBtn.isEnabled = true
            binding.selectBtn.setOnClickListener(onNotFoundBtnClick)
        }
    }

    fun onPositiveBtnClicked(block: (T) -> Unit) {
        onPositiveClick = View.OnClickListener {
            if (binding.itemRv.visibility == View.VISIBLE) {
                block.invoke(adapter.getSelectedItem())
            }
            dismiss()
        }
    }

    fun onHomeBtnClicked(block: (() -> Unit)? = null) {
        onHomeClicked = View.OnClickListener {
            block?.invoke()
            dismiss()
        }
    }

    fun onNotFoundBtnClicked(block: (() -> Unit)? = null) {
        onNotFoundBtnClick = View.OnClickListener {
            block?.invoke()
            dismiss()
        }
    }

    private fun setDefaultView() {
        binding.img.setImageResource(R.drawable.ic_search_preview)
        binding.helperTv.text = getString(R.string.check_or_create)
        binding.homeBtn.visibility = View.VISIBLE
        binding.selectBtn.isEnabled = false
        binding.notFoundView.visibility = View.VISIBLE
        binding.itemRv.visibility = View.GONE
        adapter.update(emptyList())
    }
}