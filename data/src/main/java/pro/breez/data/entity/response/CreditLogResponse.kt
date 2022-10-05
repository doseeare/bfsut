package pro.breez.data.entity.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreditLogResponse(
    val amount: String,
    val branch: Any,
    val category: Int,
    @SerializedName("credit_officer")
    val creditOfficer: Any,
    @SerializedName("credit_potential")
    val creditPotential: Any,
    val customerID: String,
    val datePay: Int,
    val office: Any,
    @SerializedName("percent_per_year")
    val percentPerYear: String,
    val period: Int,
    val productBankID: Int,
    val purposeID: Int,
    val rating: Any,
    val status: String
) : Serializable