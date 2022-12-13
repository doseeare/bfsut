package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.UnreadCountModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class UnreadCreditCountUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<UnreadCountModel, Void>() {

    override suspend fun doOnBackground(params: Void?): Result<UnreadCountModel> {
        return mainRepository.getUnreadCount()
    }

}