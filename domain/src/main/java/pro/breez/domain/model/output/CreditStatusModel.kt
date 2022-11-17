package pro.breez.domain.model.output

data class CreditStatusModel(
    val credit_request_creation_date: String,
    val customer: String,
    val full_name: String,
    val id: String,
    val read_status: Boolean,
    val status: String
)