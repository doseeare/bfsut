package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.CreditBody
import pro.breez.domain.model.output.CreditModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CreditUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<CreditModel, CreditBody>() {

    override suspend fun doOnBackground(params: CreditBody?): Result<CreditModel> {
        return mainRepository.postCredit(params!!)
    }

}