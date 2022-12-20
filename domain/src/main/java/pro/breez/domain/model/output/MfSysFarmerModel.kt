package pro.breez.domain.model.output

import java.io.Serializable

data class MfSysFarmerModel(
    val creditSpecialistFullName: String,
    val creditSpecialistPhone: String,
    val creditStatus: Int,
    val customerID: Int,
    val fatherName: String,
    val firstName: String,
    val lastName: String,
    val office: String,
    val phoneNumber: String,
) : Serializable