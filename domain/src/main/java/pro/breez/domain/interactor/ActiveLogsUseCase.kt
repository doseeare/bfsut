package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.LogsModelOut
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class ActiveLogsUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<List<LogsModelOut>, Void>() {

    override suspend fun doOnBackground(params: Void?): Result<List<LogsModelOut>> {
        return logsRepository.getActiveLogs()
    }

}