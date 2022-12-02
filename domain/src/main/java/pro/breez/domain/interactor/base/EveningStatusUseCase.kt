package pro.breez.domain.interactor.base

import pro.breez.domain.model.output.EveningModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class EveningStatusUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<EveningModel, Void>() {

    override suspend fun doOnBackground(params: Void?): Result<EveningModel> {
        return mainRepository.getEveningStatus()

    }

}