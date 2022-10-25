package pro.breez.domain.model.output

data class AddMilkModel(
    val agent: Int,
    val evening: Int,
    val farmer: String,
    val id: String,
    val morning: Int
)