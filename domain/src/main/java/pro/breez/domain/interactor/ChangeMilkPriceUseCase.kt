package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.MilkPriceModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class ChangeMilkPriceUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<MilkPriceModel, MilkPriceModel>() {

    override suspend fun doOnBackground(params: MilkPriceModel?): Result<MilkPriceModel> {
        return mainRepository.postMilkPrice(params!!)
    }

}