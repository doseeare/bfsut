package pro.breez.data.entity.body

import com.google.gson.annotations.SerializedName

class AuthBody(
    @SerializedName("username")
    val userName: String,
    @SerializedName("password")
    val password: String
)