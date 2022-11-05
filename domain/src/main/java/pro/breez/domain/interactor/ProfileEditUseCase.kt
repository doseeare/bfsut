package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.FarmerBody
import pro.breez.domain.model.output.DefaultSuccessModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class ProfileEditUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<DefaultSuccessModel, FarmerBody>() {

    override suspend fun doOnBackground(params: FarmerBody?): Result<DefaultSuccessModel> {
        return mainRepository.updateProfile(params!!)
    }

}