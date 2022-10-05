package pro.breez.domain.model.output

import java.io.Serializable

data class PaidLogModel(
    val id: String,
    val paid_date: String,
    val total_milk: Int,
    val total_sum: Int
) : Serializable