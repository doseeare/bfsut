package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.CreditStatusModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CreditsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<CreditStatusModel>, Pair<String?, String?>?>() {

    override suspend fun doOnBackground(params: Pair<String?, String?>?): Result<List<CreditStatusModel>> {
        val queries = HashMap<String, String>()
        params?.first?.let {
            queries.put("farmer_id", it)
        }
        params?.second?.let {
            queries.put("status", it)
        }
        return mainRepository.getCredits(queries)
    }

}