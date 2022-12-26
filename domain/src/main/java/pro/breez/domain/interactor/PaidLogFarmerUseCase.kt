package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.FilterBody
import pro.breez.domain.model.output.PaidLogsByFarmer
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class PaidLogFarmerUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<List<PaidLogsByFarmer>, FilterBody>() {

    override suspend fun doOnBackground(params: FilterBody?): Result<List<PaidLogsByFarmer>> {
        val queries = HashMap<String, String>()
        params?.let { body ->
            body.farmerId?.let { queries.put("farmer_id", it) }
            body.date?.let { queries.put("paid_date", it) }
            body.rangeStart?.let { queries.put("range_after", it) }
            body.rangeEnd?.let { queries.put("range_before", it) }
        }
        return logsRepository.getPaidLogByFarmer(queries)
    }

}