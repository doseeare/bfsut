package pro.breez.domain.model.output

import java.io.Serializable

data class LogsModel(
    val id: String,
    val agent: Int,
    val date: String,
    val evening: Int,
    val farmer_name: String,
    val morning_price: Int,
    val evening_price: Int,
    val morning: Int,
    val overall: String,
    val status: String,
    var paid_date: String? = null,
    var isFilter: Boolean = false,
    var isSelected: Boolean = false
) : Serializable