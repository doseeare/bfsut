package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.CategoryModelOut
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<CategoryModelOut>, Void>() {

    override suspend fun doOnBackground(params: Void?):Result<List<CategoryModelOut>> {
        return mainRepository.getCategory()
    }

}