package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class GoalUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<Pair<String, String>>, Void>() {

    override suspend fun doOnBackground(params: Void?): Result<List<Pair<String, String>>> {
        return mainRepository.getGoal()
    }

}