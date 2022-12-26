package pro.breez.domain.model.output

data class PaidLogsByFarmer(
    val paid_date: String,
    val receipts: List<PaidLog>,
    val total: Int
)

data class PaidLog(
    val date: String,
    val evening: Int,
    val milk_price: Int,
    val morning: Int,
    val overall: Int
)