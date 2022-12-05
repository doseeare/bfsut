package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AddMilkBody
import pro.breez.domain.model.input.CreditBody
import pro.breez.domain.model.input.FarmerBody
import pro.breez.domain.model.output.*
import java.io.InputStream

interface MainRepository {
    fun getFarmers(): Result<List<FarmersModel>>
    fun getUserName(): Result<UserNameModel>
    fun updateProfile(body: Pair<FarmerBody, String>): Result<DefaultSuccessModel>
    fun getFarmerProfile(id: String): Result<FarmerProfileModel>
    fun getFarmersCheck(): Result<List<FarmerCheckModel>>
    fun getProduct(id: String): Result<List<ProductsModel>>
    fun getCategory(): Result<List<CategoryModel>>
    fun getDate(): Result<List<Pair<String, String>>>
    fun getGoal(): Result<List<GoalModel>>
    fun getCredits(queries: Map<String, String>): Result<List<CreditStatusModel>>
    fun postCredit(body: CreditBody): Result<CreditModel>
    fun getMilkPrice(): Result<MilkPriceModel>
    fun postMilkPrice(body: MilkPriceModel): Result<MilkPriceModel>
    fun postMilk(body: AddMilkBody): Result<AddMilkModel>
    fun getTotalMilk(): Result<TotalMilkModel>
    fun getCreditDetail(creditId: String): Result<CreditDetailModel>
    fun getCreditIssued(queries: Map<String, String>): Result<CreditIssuedModel>
    fun getIssuedDetail(creditId: String): Result<IssuedDetailModel>
    fun getIssuedGraph(creditId: String): Result<InputStream>
    fun getEveningStatus(): Result<EveningModel>
    fun deleteLog(id : String): Result<DefaultSuccessModel>
}