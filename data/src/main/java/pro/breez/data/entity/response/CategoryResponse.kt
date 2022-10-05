package pro.breez.data.entity.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryResponse(
    val id: Int,
    @SerializedName("mfsys_id")
    val mfsysId: String,
    val name: String,
    @SerializedName("parent_id")
    val parentId: String
) : Serializable