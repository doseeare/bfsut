package pro.breez.domain.model.output

data class CreditPeriodModel(
    val period_max: Int,
    val period_min: Int
)

data class PeriodModel(
    val period: String,
    val inDigit: Int
)