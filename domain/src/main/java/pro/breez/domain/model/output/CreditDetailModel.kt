package pro.breez.domain.model.output

data class CreditDetailModel(
    val amount: String,
    val branch: String,
    val category: String,
    val credit_officer: String,
    val credit_potential: Int,
    val credit_request_creation_date: String,
    val customer: String,
    val date_pay: Int,
    val full_name: String,
    val id: String,
    val monthly_payment_amount: String,
    val office: String,
    val percent_per_year: String,
    val period: Int,
    val product_bank: String,
    val purpose: String,
    val rating: Int,
    val read_status: Boolean,
    val purpose_comment: String,
    val status: String
)