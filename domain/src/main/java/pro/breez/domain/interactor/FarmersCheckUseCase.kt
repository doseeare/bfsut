package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.FarmerCheckModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class FarmersCheckUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<FarmerCheckModel>, Void>() {

    override suspend fun doOnBackground(params: Void?): Result<List<FarmerCheckModel>> {
        return mainRepository.getFarmersCheck()
    }

}