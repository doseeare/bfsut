package pro.breez.domain.model.output

data class CreditSearchFarmerModel(
    val credit_specialist_full_name: String,
    val credit_specialist_phone: String,
    val credit_status: Boolean,
    val customer_id: Int,
    val father_name: String,
    val first_name: String,
    val last_name: String,
    val office: String,
    val phone_number: String
)

