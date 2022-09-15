package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.CreditModelIn
import pro.breez.domain.model.output.CreditModelOut
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CreditUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<CreditModelOut, CreditModelIn>() {

    override suspend fun doOnBackground(params: CreditModelIn?): Result<CreditModelOut> {
        return mainRepository.postCredit(params!!)
    }

}