package pro.breez.domain.model.output

data class CreditIssuedModel(
    val data: List<IssuedData>,
    val total_credit: Int,
    val total_sum: Int
)

data class IssuedData(
    val amount: Int,
    val date: String? = null,
    val date_disburse_plan: String? = null,
    val full_name: String,
    val id: String,
    val is_overdue: Boolean? = null,
    val monthly_payment_amount: Int,
    val overdue: Int? = null
)