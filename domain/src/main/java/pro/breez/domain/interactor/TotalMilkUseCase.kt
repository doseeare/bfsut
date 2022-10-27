package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.TotalMilkModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class TotalMilkUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<TotalMilkModel, Unit>() {

    override suspend fun doOnBackground(params: Unit?): Result<TotalMilkModel> {
        return mainRepository.getTotalMilk()
    }

}