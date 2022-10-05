package pro.breez.domain.model.output

import java.io.Serializable

class FarmersModel(
    val id: String,
    val father_name: String,
    val first_name: String,
    val is_picked: Boolean,
    val last_name: String
): Serializable