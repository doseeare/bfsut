package pro.breez.domain.model.output

import java.io.Serializable

data class FarmerCheckModel(
    val evening: Int,
    val full_name: String,
    val id: String,
    val is_picked: String,
    val morning: Int
) : Serializable