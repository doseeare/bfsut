package pro.breez.domain.model.output

data class TotalMilkModel(
    val evening_milk_sum: Int,
    val evening_price_sum: Int,
    val morning_milk_sum: Int,
    val morning_price_sum: Int
)