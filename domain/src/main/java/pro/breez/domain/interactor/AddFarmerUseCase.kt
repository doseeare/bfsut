package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.FarmerBody
import pro.breez.domain.model.output.DefaultSuccessModel
import pro.breez.domain.model.output.MfSysModel
import pro.breez.domain.repository.AddFarmerRepository
import javax.inject.Inject

class AddFarmerUseCase @Inject constructor(
    private val addFarmerRepository: AddFarmerRepository
) : UseCase<DefaultSuccessModel, FarmerBody>() {

    override suspend fun doOnBackground(params: FarmerBody?): Result<DefaultSuccessModel> {
        return addFarmerRepository.postFarmer(params!!)
    }

}