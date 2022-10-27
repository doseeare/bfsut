package pro.breez.domain.model.input

data class AddMilkBody(
    var evening: Int = 0,
    val farmer: String,
    var morning: Int = 0
)