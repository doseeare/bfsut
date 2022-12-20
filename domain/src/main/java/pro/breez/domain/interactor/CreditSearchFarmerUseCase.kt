package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.CreditSearchFarmerModel
import pro.breez.domain.model.output.MfSysFarmerModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CreditSearchFarmerUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<CreditSearchFarmerModel>, String?>() {

    override suspend fun doOnBackground(params: String?): Result<List<CreditSearchFarmerModel>> {
        val queries = HashMap<String, String>()

        params?.let { param ->
            val isDigit = params.all { it.isDigit() }
            if (isDigit) {
                queries["tax_number"] = param
            } else {
                queries["full_name"] = param
            }
        }
        return mainRepository.creditSearchFarmer(queries)
    }

}