package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.CalculateModel
import pro.breez.domain.repository.LogsRepository
import javax.inject.Inject

class CalculateActiveLogUseCase @Inject constructor(
    private val logsRepository: LogsRepository
) : UseCase<CalculateModel, String>() {

    override suspend fun doOnBackground(params: String?): Result<CalculateModel> {
        return logsRepository.calculateActiveLog(params!!)
    }

}