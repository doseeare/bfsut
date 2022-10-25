package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.AddMilkBody
import pro.breez.domain.model.output.AddMilkModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class AddMilkUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<AddMilkModel, AddMilkBody>() {

    override suspend fun doOnBackground(params: AddMilkBody?): Result<AddMilkModel> {
        return mainRepository.postMilk(params!!)
    }

}