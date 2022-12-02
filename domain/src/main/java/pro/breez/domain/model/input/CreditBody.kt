package pro.breez.domain.model.input

data class CreditBody(
    val amount: String,
    val category: Int,
    val mfsys_customer_id: Int,
    val date_disburse_plan: String,
    val date_pay: Int,
    val period: Int,
    val product_bank: Int,
    val purpose: Int,
    val purpose_comment: String,
    val office: String?,
    val credit_officer: String?
)