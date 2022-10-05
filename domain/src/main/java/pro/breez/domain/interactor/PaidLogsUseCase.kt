package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.PaidLogModel
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class PaidLogsUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<List<PaidLogModel>, Void>() {

    override suspend fun doOnBackground(params: Void?): Result<List<PaidLogModel>> {
        return logsRepository.getPaidLogs()
    }
}