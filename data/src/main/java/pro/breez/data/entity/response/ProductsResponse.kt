package pro.breez.data.entity.response

import java.io.Serializable

data class ProductsResponse(
    val id: Int,
    val mfsys_id: String,
    val name: String,
    val parent_id: String
): Serializable