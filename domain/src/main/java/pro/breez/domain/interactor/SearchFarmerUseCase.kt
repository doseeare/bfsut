package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.MfSysFarmerModel
import pro.breez.domain.repository.AddFarmerRepository
import javax.inject.Inject

class SearchFarmerUseCase @Inject constructor(
    private val addFarmerRepository: AddFarmerRepository
) : UseCase<List<MfSysFarmerModel>, String?>() {

    override suspend fun doOnBackground(params: String?): Result<List<MfSysFarmerModel>> {
        val queries = HashMap<String, String>()

        params?.let { param ->
            val isDigit = params.all { it.isDigit() }
            if (isDigit) {
                queries["tax_number"] = param
            } else {
                queries["full_name"] = param
            }
        }
        return addFarmerRepository.searchFarmer(queries)
    }

}