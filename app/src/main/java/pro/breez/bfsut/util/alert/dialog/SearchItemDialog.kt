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

class SearchItemDialog<T>(
    private val valueName: Array<String>,
) :
    DialogFragment() {
    private lateinit var binding: DialogItemSearchBinding

    private var onPositiveClick: View.OnClickListener? = null
    private var onKeyChanged: ((String) -> Unit)? = null

    private lateinit var adapter: SelectorItemAdapter<T>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogItemSearchBinding.inflate(inflater, container, false)
        requireDialog().window?.setBackgroundDrawableResource(android.R.color.transparent)
        adapter = SelectorItemAdapter(arrayListOf(), valueName, activeBtn = {
            binding.selectBtn.isEnabled = true
        })
        binding.itemRv.adapter = adapter
        binding.selectBtn.setOnClickListener(onPositiveClick)
        onEditTextFilled()
        return binding.root
    }

    private fun onEditTextFilled() {
        var mLastClickTime = 0L
        binding.search.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank())
                if (SystemClock.elapsedRealtime() - mLastClickTime > 500L) {
                    mLastClickTime = SystemClock.elapsedRealtime()
                    onKeyChanged?.invoke(text.toString())
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
            binding.selectBtn.text = "Выбрать"
        } else {
            binding.notFoundView.visibility = View.VISIBLE
            binding.itemRv.visibility = View.GONE
            binding.img.setImageResource(R.drawable.ic_search_not_found)
            binding.helperTv.text = getString(R.string.need_to_create)
            binding.selectBtn.text = "Создать"
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
}