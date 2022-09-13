package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.AuthModelIn
import pro.breez.domain.model.output.AuthModelOut
import pro.breez.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val accountRepository: AuthRepository
) : UseCase<AuthModelOut, AuthModelIn>() {

    override suspend fun doOnBackground(params: AuthModelIn?): Result<AuthModelOut> {
        return accountRepository.authUser(params!!)
    }

}