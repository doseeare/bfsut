package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.ReadStatusBody
import pro.breez.domain.model.output.DefaultSuccessModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class ReadStatusUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<DefaultSuccessModel, ReadStatusBody>() {

    override suspend fun doOnBackground(params: ReadStatusBody?): Result<DefaultSuccessModel> {
        return mainRepository.readStatusUpdate(params!!)
    }

}