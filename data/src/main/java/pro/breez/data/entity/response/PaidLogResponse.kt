package pro.breez.data.entity.response

data class PaidLogResponse(
    val id: String,
    val paid_date: String,
    val total_milk: Int,
    val total_sum: Int
)