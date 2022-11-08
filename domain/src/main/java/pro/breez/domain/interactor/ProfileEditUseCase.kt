package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.FarmerBody
import pro.breez.domain.model.output.DefaultSuccessModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class ProfileEditUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<DefaultSuccessModel, Pair<FarmerBody, String>>() {

    override suspend fun doOnBackground(params: Pair<FarmerBody, String>?): Result<DefaultSuccessModel> {
        return mainRepository.updateProfile(params!!)
    }

}