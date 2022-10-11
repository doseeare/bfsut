package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AuthBody
import pro.breez.domain.model.output.AuthModel

interface AuthRepository {
    fun authUser(authBody: AuthBody): Result<AuthModel>
}