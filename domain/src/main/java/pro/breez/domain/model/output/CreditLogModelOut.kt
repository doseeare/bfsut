package pro.breez.domain.model.output

data class CreditLogModelOut(
    val amount: String,
    val branch: Any,
    val category: Int,
    val credit_officer: Any,
    val credit_potential: Any,
    val customerID: String,
    val datePay: Int,
    val office: Any,
    val percent_per_year: String,
    val period: Int,
    val productBankID: Int,
    val purposeID: Int,
    val rating: Any,
    val status: String
)