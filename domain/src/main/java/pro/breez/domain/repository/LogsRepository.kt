package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CalculateActiveBody
import pro.breez.domain.model.input.MilkChangesBody
import pro.breez.domain.model.output.LogsModel
import pro.breez.domain.model.output.MilkModel
import pro.breez.domain.model.output.PaidLogModel
import pro.breez.domain.model.output.PaidLogsDetailModel

interface LogsRepository {
    fun getActiveLogs(): Result<List<LogsModel>>
    fun getPaidLogs(): Result<List<PaidLogModel>>
    fun getPaidLogDetail(id: String): Result<List<PaidLogsDetailModel>>
    fun getAllLogs(): Result<List<LogsModel>>
    fun calculateActiveLog(id: String): Result<String>
    fun calculateActiveLogs(body: CalculateActiveBody): Result<String>
    fun saveMilkChanges(id: String, changes: MilkChangesBody): Result<MilkModel>
}