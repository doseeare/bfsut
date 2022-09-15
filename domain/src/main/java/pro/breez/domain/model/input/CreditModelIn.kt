package pro.breez.domain.model.input

data class CreditModelIn(
    val amount: String,
    val category: Int,
    val customerID: String,
    val datePay: Int,
    val period: Int,
    val productBankID: Int,
    val purposeComment: String,
    val purposeID: Int
)