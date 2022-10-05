package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.GoalModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class GoalUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<GoalModel>, Void>() {

    override suspend fun doOnBackground(params: Void?):  Result<List<GoalModel>> {
        return mainRepository.getGoal()
    }

}