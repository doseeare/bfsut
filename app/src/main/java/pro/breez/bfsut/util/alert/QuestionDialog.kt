package pro.breez.bfsut.util.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import pro.breez.bfsut.databinding.FragmentQuestionDialogBinding


class QuestionDialog() : DialogFragment() {

    private lateinit var binding: FragmentQuestionDialogBinding

    private var title = ""
    private var onPositiveClick: View.OnClickListener? = null
    private var onNegativeClick: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionDialogBinding.inflate(inflater, container, false)
        requireDialog().window?.setBackgroundDrawableResource(android.R.color.transparent)
        requireDialog().setCancelable(false)
        setDefaultNegativeBtn()
        binding.title.text = title
        binding.positiveBtn.setOnClickListener(onPositiveClick)
        binding.negativeBtn.setOnClickListener(onNegativeClick)
        return binding.root
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun onPositiveBtnClicked(block: () -> Unit) {
        onPositiveClick = View.OnClickListener {
            block.invoke()
        }
    }

    private fun setDefaultNegativeBtn() {
        if (onNegativeClick == null) {
            onNegativeClick = View.OnClickListener {
                dismiss()
            }
        }
    }

    fun onNegativeBtnClicked(block: (() -> Unit)? = null) {
        onNegativeClick = View.OnClickListener {
            if (block == null)
                requireDialog().dismiss()
            else
                block.invoke()
        }
    }
}