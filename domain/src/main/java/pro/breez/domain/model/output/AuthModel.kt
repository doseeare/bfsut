package pro.breez.domain.model.output

import java.io.Serializable

class AuthModel(
    val access_token: String,
    val username: String
) : Serializable