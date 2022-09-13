package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AuthModelIn
import pro.breez.domain.model.output.AuthModelOut
import pro.breez.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference,
) : AuthRepository {

    override fun authUser(authBody: AuthModelIn): Result<AuthModelOut> {
        return restClient.authApi.authUser(authBody).apply {
            dataPreference.token = out()?.access_token
        }
    }

}