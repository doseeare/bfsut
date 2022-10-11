package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AuthBody
import pro.breez.domain.model.output.AuthModel
import pro.breez.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference,
) : AuthRepository {

    override fun authUser(authBody: AuthBody): Result<AuthModel> {
        return restClient.authApi.authUser(authBody).apply {
            performOnSuccess {
                dataPreference.token = it.access_token
            }
        }
    }
}