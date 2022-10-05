package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CalculateActiveLogsIn
import pro.breez.domain.model.output.LogsModel
import pro.breez.domain.model.output.PaidLogModel
import pro.breez.domain.model.output.PaidLogsDetailModel
import pro.breez.domain.repository.LogsRepository

class LogsRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference
) : LogsRepository {

    override fun getActiveLogs(): Result<List<LogsModel>> {
        return restClient.mainApi.getLogs(dataPreference.token, "active")
    }

    override fun getPaidLogs(): Result<List<PaidLogModel>> {
        return restClient.mainApi.getPaidLogs(dataPreference.token)
    }

    override fun getPaidLogDetail(id : String): Result<List<PaidLogsDetailModel>> {
        return restClient.mainApi.getPaidLogDetail(dataPreference.token, id)
    }

    override fun getAllLogs(): Result<List<LogsModel>> {
        return restClient.mainApi.getLogs(dataPreference.token, "")
    }

    override fun calculateActiveLog(id: String): Result<String> {
        return restClient.mainApi.calculateLog(dataPreference.token, id)
    }

    override fun calculateActiveLogs(body: CalculateActiveLogsIn): Result<String> {
        return restClient.mainApi.calculateLogs(dataPreference.token, body)
    }

}