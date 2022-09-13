package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AuthModelIn
import pro.breez.domain.model.output.AuthModelOut

interface AuthRepository {
    fun authUser(authBody: AuthModelIn): Result<AuthModelOut>
}