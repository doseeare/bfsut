package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase

class Auth() : UseCase<Any, Void>() {
    override suspend fun doOnBackground(params: Void?): Result<Any> {
        return Result.Success<String>("dsa")
    }
}