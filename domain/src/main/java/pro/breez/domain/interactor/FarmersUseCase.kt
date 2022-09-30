package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.FarmersModelOut
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class FarmersUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<FarmersModelOut>, Void>() {

    override suspend fun doOnBackground(params: Void?):Result<List<FarmersModelOut>> {
        return mainRepository.getFarmers()
    }

}