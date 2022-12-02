package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.FilterBody
import pro.breez.domain.model.output.PaidLogModel
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class PaidLogsUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<List<PaidLogModel>, FilterBody?>() {

    override suspend fun doOnBackground(params: FilterBody?): Result<List<PaidLogModel>> {
        val queries = HashMap<String, String>()
        params?.let { body ->
            body.date?.let { queries.put("paid_date", it) }
            body.rangeStart?.let { queries.put("range_after", it) }
            body.rangeEnd?.let { queries.put("range_before", it) }
        }
        return logsRepository.getPaidLogs(queries)
    }
}