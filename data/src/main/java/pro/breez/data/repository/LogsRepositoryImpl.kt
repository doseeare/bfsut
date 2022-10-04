package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CalculateActiveLogsIn
import pro.breez.domain.model.output.LogsModelOut
import pro.breez.domain.repository.LogsRepository

class LogsRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference
) : LogsRepository {

    override fun getActiveLogs(): Result<List<LogsModelOut>> {
        return restClient.mainApi.getLogs(dataPreference.token, "active")
    }

    override fun getPaidLogs(): Result<List<LogsModelOut>> {
        return restClient.mainApi.getLogs(dataPreference.token, "")
    }

    override fun getAllLogs(): Result<List<LogsModelOut>> {
        return restClient.mainApi.getLogs(dataPreference.token, "")
    }

    override fun calculateActiveLog(id: String): Result<String> {
        return restClient.mainApi.calculateLog(dataPreference.token, id)
    }

    override fun calculateActiveLogs(body : CalculateActiveLogsIn): Result<String> {
        return restClient.mainApi.calculateLogs(dataPreference.token, body)
    }

}