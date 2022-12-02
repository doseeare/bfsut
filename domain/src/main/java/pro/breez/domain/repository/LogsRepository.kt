package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CalculateActiveBody
import pro.breez.domain.model.input.MilkChangesBody
import pro.breez.domain.model.output.*

interface LogsRepository {
    fun getActiveLogs(queries: Map<String, String>): Result<List<LogsModel>>
    fun getPaidLogs(queries: Map<String, String>): Result<List<PaidLogModel>>
    fun getPaidLogDetail(id: String): Result<List<PaidLogsDetailModel>>
    fun getAllLogs(queries: Map<String, String>): Result<List<LogsModel>>
    fun calculateActiveLog(id: String): Result<CalculateModel>
    fun calculateActiveLogs(body: CalculateActiveBody): Result<CalculateModel>
    fun saveMilkChanges(id: String, changes: MilkChangesBody): Result<MilkModel>
}