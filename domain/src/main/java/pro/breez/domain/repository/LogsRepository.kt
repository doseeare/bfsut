package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CalculateActiveLogsIn
import pro.breez.domain.model.output.LogsModelOut

interface LogsRepository {
    fun getActiveLogs(): Result<List<LogsModelOut>>
    fun getPaidLogs(): Result<List<LogsModelOut>>
    fun getAllLogs(): Result<List<LogsModelOut>>
    fun calculateActiveLog(id: String): Result<String>
    fun calculateActiveLogs(body : CalculateActiveLogsIn): Result<String>
}