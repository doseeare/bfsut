package pro.breez.data.entity.response

data class PaidLogsDetailResponse(
    val agent: Int,
    val date: String,
    val evening: Int,
    val farmer_name: String,
    val id: String,
    val milk_price: String,
    val morning: Int,
    val overall: Int,
    val status: String
)