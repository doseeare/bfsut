package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.CalculateActiveLogsIn
import pro.breez.domain.model.output.LogsModelOut
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class CalculateActiveLogsUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<String, ArrayList<LogsModelOut>>() {

    override suspend fun doOnBackground(params: ArrayList<LogsModelOut>?): Result<String> {
        val listOfId = params?.map { it.id }!!
        val body = CalculateActiveLogsIn(listOfId)
        return logsRepository.calculateActiveLogs(body)
    }

}