package pro.breez.domain.model.input

data class FarmerBody(
    val actual_address: String? = null,
    val actual_apartment: String? = null,
    val actual_country: Int? = null,
    val actual_house: String? = null,
    val actual_region: Int? = null,
    val actual_state: Int? = null,
    val actual_village: String? = null,
    val address: String? = null,
    val apartment: String? = null,
    val birth_date: String? = null,
    val country: Int? = null,
    val document_date: String? = null,
    val document_issue: Int? = null,
    val document_issue_number: String? = null,
    val document_number: String? = null,
    val document_series: String? = null,
    val document_type: Int? = null,
    val education: Int? = null,
    val family_count: Int? = null,
    val father_name: String? = null,
    val first_name: String? = "KOTOKBASH",
    val gender: Int? = null,
    val house: String? = null,
    val is_actual_address_match: Boolean? = null,
    val job: String? = null,
    val job_address: String? = null,
    val job_position: String? = null,
    val last_name: String? = null,
    val marital_status: String? = null,
    val nationality: Int? = null,
    val phone_number: String? = null,
    val phone_number_additional: String? = null,
    val place_of_birth: String? = null,
    val purpose: Int? = null,
    val region: Int? = null,
    val resident: Int? = null,
    val spouse_customer_id: String? = null,
    val state: Int? = null,
    val tax_number: String? = null,
    val village: String? = null
)