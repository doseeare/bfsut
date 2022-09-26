package pro.breez.domain.model.input

data class CreditModelIn(
    val amount: String,
    val category: Int,
    val customer: String,
    val date_disburse_plan: String,
    val date_pay: Int,
    val period: Int,
    val product_bank: Int,
    val purpose: Int,
    val purpose_comment: String,
)