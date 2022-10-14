package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.FilterBody
import pro.breez.domain.model.output.LogsModel
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class ActiveLogsUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<List<LogsModel>, FilterBody>() {

    override suspend fun doOnBackground(params: FilterBody?): Result<List<LogsModel>> {
        val queries = HashMap<String, String>()
        params?.let { body ->
            body.farmerId?.let { queries.put("farmer_id", it) }
            body.date?.let { queries.put("date", it) }
            body.rangeAfter?.let { queries.put("range_after", it) }
            body.rangeBefore?.let { queries.put("range_before", it) }
        }
        queries.put("status", "active")
        return logsRepository.getActiveLogs(queries)
    }

}