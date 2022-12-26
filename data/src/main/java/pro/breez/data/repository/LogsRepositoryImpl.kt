package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CalculateActiveBody
import pro.breez.domain.model.input.MilkChangesBody
import pro.breez.domain.model.output.*
import pro.breez.domain.repository.LogsRepository

class LogsRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference
) : LogsRepository {

    override fun getActiveLogs(queries: Map<String, String>): Result<List<LogsModel>> {
        return restClient.mainApi.getActiveLogs(dataPreference.token, queries)
    }

    override fun getPaidLogByFarmer(queries: Map<String, String>): Result<List<PaidLogsByFarmer>> {
        return restClient.mainApi.getPaidLogsByFarmer(dataPreference.token, queries)
    }

    override fun getPaidLogs(queries: Map<String, String>): Result<List<PaidLogModel>> {
        return restClient.mainApi.getPaidLogs(dataPreference.token, queries)
    }

    override fun getPaidLogDetail(id: String): Result<List<PaidLogsDetailModel>> {
        return restClient.mainApi.getPaidLogDetail(dataPreference.token, id)
    }

    override fun getAllLogs(queries: Map<String, String>): Result<List<LogsModel>> {
        return restClient.mainApi.getAllLogs(dataPreference.token, queries)
    }

    override fun calculateActiveLog(id: String): Result<CalculateModel> {
        return restClient.mainApi.calculateLog(dataPreference.token, id)
    }

    override fun calculateActiveLogs(body: CalculateActiveBody): Result<CalculateModel> {
        return restClient.mainApi.calculateLogs(dataPreference.token, body)
    }

    override fun saveMilkChanges(id: String, changes: MilkChangesBody): Result<MilkModel> {
        return restClient.mainApi.saveMilkChanges(dataPreference.token, id, changes)
    }

}