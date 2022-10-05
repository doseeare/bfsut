package pro.breez.data.rest.api

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AuthModelIn
import pro.breez.domain.model.output.AuthModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("v1/agents/auth")
    fun authUser(@Body body: AuthModelIn): Result<AuthModel>
}