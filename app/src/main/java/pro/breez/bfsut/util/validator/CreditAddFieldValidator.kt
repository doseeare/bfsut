package pro.breez.bfsut.util.validator

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
        binding.date,
        binding.dateOfPayment,
        binding.sum
    )

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
}