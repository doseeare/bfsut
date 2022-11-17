package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.FarmerBody
import pro.breez.domain.model.output.CreditDetailModel
import pro.breez.domain.model.output.DefaultSuccessModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CreditDetailUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<CreditDetailModel, String>() {

    override suspend fun doOnBackground(params: String?): Result<CreditDetailModel> {
        return mainRepository.getCreditDetail(params!!)
    }

}