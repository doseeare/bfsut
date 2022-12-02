package pro.breez.domain.model.output

import java.io.Serializable

data class FarmerCheckModel(
    val evening: Int,
    val evening_price: Int,
    val full_name: String,
    val id: String,
    val is_picked: String,
    val morning: Int,
    val morning_price: Int,
    val report_id: String? = null
) : Serializable