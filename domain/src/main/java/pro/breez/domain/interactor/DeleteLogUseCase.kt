package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.DefaultSuccessModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class DeleteLogUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<DefaultSuccessModel, String>() {

    override suspend fun doOnBackground(params: String?): Result<DefaultSuccessModel> {
        return mainRepository.deleteLog(params!!)
    }

}