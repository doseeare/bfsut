package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.PaidLogsDetailModel
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class PaidLogDetailUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<List<PaidLogsDetailModel>, String>() {

    override suspend fun doOnBackground(params: String?): Result<List<PaidLogsDetailModel>> {
        return logsRepository.getPaidLogDetail(params!!)
    }
}