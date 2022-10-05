package pro.breez.data.entity.response

import java.io.Serializable

class FarmersResponse(
    val id: String,
    val father_name: String,
    val first_name: String,
    val is_picked: Boolean,
    val last_name: String
): Serializable