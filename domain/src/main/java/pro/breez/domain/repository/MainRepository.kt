package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CreditModelIn
import pro.breez.domain.model.output.*

interface MainRepository {
    fun getFarmers(): Result<List<FarmersModelOut>>
    fun getProduct(id : String): Result<List<ProductsModelOut>>
    fun getCategory():Result<List<CategoryModelOut>>
    fun getDate(): Result<List<Pair<String, String>>>
    fun getGoal():  Result<List<GoalModelOut>>
    fun getCredits(): Result<List<CreditLogModelOut>>
    fun postCredit(body : CreditModelIn): Result<CreditModelOut>
}