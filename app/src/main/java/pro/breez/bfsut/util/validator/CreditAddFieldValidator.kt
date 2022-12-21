package pro.breez.bfsut.util.validator

import androidx.core.widget.doOnTextChanged
import pro.breez.bfsut.databinding.FragmentCreditAddBinding
import pro.breez.bfsut.util.ifFalse
import pro.breez.bfsut.util.ifTrue

class CreditAddFieldValidator(val binding: FragmentCreditAddBinding) {

    private val importantFields = listOf(
        binding.farmer,
        binding.goal,
        binding.commentsOfGoal,
        binding.product,
        binding.category,
        binding.dateDisburseEt,
        binding.period,
        binding.dateOfPayment,
        binding.sum,
        binding.period,
    )

    private fun validateSendBtn() {
        val allNotNull = importantFields.all { it.textOrNull() != null }
        binding.sendBtn.isEnabled = allNotNull
    }

    fun validateFields(): Boolean {
        var result = false
        for (field in importantFields) {
            val fieldStatus = field.ifEmptyError()
            fieldStatus.ifTrue {
                result.ifFalse {
                    result = fieldStatus
                }
            }
        }
        return !result
    }

    fun enableSendBtnOnEdit() {
        for (field in importantFields) {
            field.editText.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotBlank()) {
                    validateSendBtn()
                }
            }
        }
    }
}