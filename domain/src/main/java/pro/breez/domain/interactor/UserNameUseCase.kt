package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.UserNameModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class UserNameUseCase @Inject constructor(
    private val mainRepository: MainRepository,
) : UseCase<UserNameModel, String>() {

    override suspend fun doOnBackground(params: String?): Result<UserNameModel> {
        return mainRepository.getUserName()
    }

}