package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.ProductsModelOut
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<ProductsModelOut>, String>() {

    override suspend fun doOnBackground(params: String?): Result<List<ProductsModelOut>> {
        return mainRepository.getProduct(params!!)
    }

}