package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<Pair<String, String>>, String>() {

    override suspend fun doOnBackground(params: String?): Result<List<Pair<String, String>>> {
        return mainRepository.getProduct(params!!)
    }

}