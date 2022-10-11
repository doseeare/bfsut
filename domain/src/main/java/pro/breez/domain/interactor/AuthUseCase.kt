package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.AuthBody
import pro.breez.domain.model.output.AuthModel
import pro.breez.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val accountRepository: AuthRepository
) : UseCase<AuthModel, AuthBody>() {

    override suspend fun doOnBackground(params: AuthBody?): Result<AuthModel> {
        return accountRepository.authUser(params!!)
    }

}