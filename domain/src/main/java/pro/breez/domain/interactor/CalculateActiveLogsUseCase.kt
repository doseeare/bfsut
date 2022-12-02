package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.CalculateActiveBody
import pro.breez.domain.model.output.CalculateModel
import pro.breez.domain.model.output.LogsModel
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class CalculateActiveLogsUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<CalculateModel, ArrayList<LogsModel>>() {

    override suspend fun doOnBackground(params: ArrayList<LogsModel>?): Result<CalculateModel> {
        val listOfId = params?.map { it.id }!!
        val body = CalculateActiveBody(listOfId)
        return logsRepository.calculateActiveLogs(body)
    }

}