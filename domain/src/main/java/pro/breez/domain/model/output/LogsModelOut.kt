package pro.breez.domain.model.output

data class LogsModelOut(
    val id: String,
    val agent: Int,
    val date: String,
    val evening: Int,
    val farmer_name: String,
    val milk_price: String,
    val morning: Int,
    val overall: String,
    val status: String,
    var isSelected: Boolean = false
)