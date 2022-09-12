package pro.breez.data.entity

import com.google.gson.annotations.SerializedName

data class AccountEntity(
    val currency:String,
    val account:Long,
    val balance:String,
    @SerializedName("available", alternate = ["avialable"])
    val available:String
)
