package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.MilkChangesBody
import pro.breez.domain.model.output.MilkModel
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class SaveMilkChangesUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<MilkModel, Pair<String, MilkChangesBody>>() {

    override suspend fun doOnBackground(params: Pair<String, MilkChangesBody>?): Result<MilkModel> {
        return logsRepository.saveMilkChanges(params!!.first, params.second)
    }

}