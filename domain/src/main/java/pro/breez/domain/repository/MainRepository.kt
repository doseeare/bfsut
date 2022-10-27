package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AddMilkBody
import pro.breez.domain.model.input.CreditBody
import pro.breez.domain.model.output.*

interface MainRepository {
    fun getFarmers(): Result<List<FarmersModel>>
    fun getProduct(id: String): Result<List<ProductsModel>>
    fun getCategory(): Result<List<CategoryModel>>
    fun getDate(): Result<List<Pair<String, String>>>
    fun getGoal(): Result<List<GoalModel>>
    fun getCredits(): Result<List<CreditLogModel>>
    fun postCredit(body: CreditBody): Result<CreditModel>
    fun getMilkPrice(): Result<MilkPriceModel>
    fun postMilkPrice(body : MilkPriceModel) : Result<MilkPriceModel>
    fun postMilk(body : AddMilkBody) : Result<AddMilkModel>
    fun getTotalMilk () : Result<TotalMilkModel>
}