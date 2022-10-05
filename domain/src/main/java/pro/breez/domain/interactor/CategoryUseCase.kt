package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.output.CategoryModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<List<CategoryModel>, Void>() {

    override suspend fun doOnBackground(params: Void?):Result<List<CategoryModel>> {
        return mainRepository.getCategory()
    }

}