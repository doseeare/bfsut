package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.IssuedDetailModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class IssuedDetailUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<IssuedDetailModel, String>() {
    override suspend fun doOnBackground(params: String?): Result<IssuedDetailModel> {
        return mainRepository.getIssuedDetail(params!!)
    }
}