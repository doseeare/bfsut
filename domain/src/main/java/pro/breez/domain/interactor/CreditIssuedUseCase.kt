package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.CreditIssuedBody
import pro.breez.domain.model.output.CreditIssuedModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CreditIssuedUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<CreditIssuedModel, CreditIssuedBody>() {

    override suspend fun doOnBackground(params: CreditIssuedBody?): Result<CreditIssuedModel> {
        val queries = HashMap<String, String>()
        params?.apply {
            farmerId?.let {
                queries["farmer_id"] = it
            }
            date?.let {
                queries["issued_date"] = it
            }
            rangeAfter?.let {
                queries["range_after"] = it
            }
            rangeBefore?.let {
                queries["range_before"] = it
            }
            status?.let {
                queries["status"] = it
            }
        }
        return mainRepository.getCreditIssued(queries)
    }

}