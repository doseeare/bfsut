package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.MfSysModel
import pro.breez.domain.repository.AddFarmerRepository
import javax.inject.Inject

class EducationUseCase @Inject constructor(
    private val addFarmerRepository: AddFarmerRepository
) : UseCase<List<MfSysModel>, Unit>() {

    override suspend fun doOnBackground(params: Unit?): Result<List<MfSysModel>> {
        return addFarmerRepository.getEducations()
    }

}