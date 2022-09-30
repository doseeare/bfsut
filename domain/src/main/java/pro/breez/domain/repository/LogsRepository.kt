package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.output.LogsModelOut

interface LogsRepository {
    fun getActiveLogs(): Result<List<LogsModelOut>>
    fun getPaidLogs(): Result<List<LogsModelOut>>
    fun getAllLogs(): Result<List<LogsModelOut>>
}