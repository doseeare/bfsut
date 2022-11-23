package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.IssuedDetailModel
import pro.breez.domain.model.output.IssuedGraphModel
import pro.breez.domain.repository.MainRepository
import java.io.InputStream
import javax.inject.Inject

class IssuedGraphUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<InputStream, String>() {
    override suspend fun doOnBackground(params: String?): Result<InputStream> {
        return mainRepository.getIssuedGraph(params!!)
    }
}