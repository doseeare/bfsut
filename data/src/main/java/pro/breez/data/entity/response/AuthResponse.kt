package pro.breez.data.entity.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AuthResponse(
    @SerializedName("access_token")
    val token: String
): Serializable