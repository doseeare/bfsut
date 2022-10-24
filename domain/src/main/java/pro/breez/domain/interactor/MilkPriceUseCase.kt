package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.MilkPriceModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class MilkPriceUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<MilkPriceModel, Unit>() {

    override suspend fun doOnBackground(params: Unit?): Result<MilkPriceModel> {
        return mainRepository.getMilkPrice()
    }

}