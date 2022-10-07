package pro.breez.domain.interactor.base

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.LogsModel
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class AllLogsUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<List<LogsModel>, Void>() {

    override suspend fun doOnBackground(params: Void?): Result<List<LogsModel>> {
        return logsRepository.getAllLogs()
    }

}