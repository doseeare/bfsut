package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.FarmerProfileModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class FarmerProfileUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<FarmerProfileModel, String>() {

    override suspend fun doOnBackground(params: String?): Result<FarmerProfileModel> {
        return mainRepository.getFarmerProfile(params!!)
    }

}