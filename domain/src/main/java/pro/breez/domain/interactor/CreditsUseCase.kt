package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.CreditLogModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CreditsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<CreditLogModel>, Void>() {

    override suspend fun doOnBackground(params: Void?): Result<List<CreditLogModel>> {
        return mainRepository.getCredits()
    }

}