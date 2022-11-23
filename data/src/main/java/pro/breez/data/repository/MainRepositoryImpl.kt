package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AddMilkBody
import pro.breez.domain.model.input.CreditBody
import pro.breez.domain.model.input.FarmerBody
import pro.breez.domain.model.output.*
import pro.breez.domain.repository.MainRepository
import java.io.InputStream

class MainRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference
) : MainRepository {

    override fun getFarmers(): Result<List<FarmersModel>> {
        return restClient.mainApi.getFarmers(dataPreference.token)
    }

    override fun getUserName(): Result<UserNameModel> {
        return restClient.mainApi.getUserName(dataPreference.token)
    }

    override fun updateProfile(body: Pair<FarmerBody, String>): Result<DefaultSuccessModel> {
        return restClient.mainApi.updateProfile(dataPreference.token, body.first, body.second)
    }

    override fun getFarmerProfile(id: String): Result<FarmerProfileModel> {
        return restClient.mainApi.getFarmerProfile(dataPreference.token, id)
    }

    override fun getFarmersCheck(): Result<List<FarmerCheckModel>> {
        return restClient.mainApi.getFarmersCheck(dataPreference.token)
    }

    override fun getProduct(id: String): Result<List<ProductsModel>> {
        return restClient.mainApi.getProduct(dataPreference.token, id)
    }


    override fun getCategory(): Result<List<CategoryModel>> {
        return restClient.mainApi.getCategory(dataPreference.token)
    }

    override fun getDate(): Result<List<Pair<String, String>>> {
        return restClient.mainApi.getDate(dataPreference.token).map {
            it.map { date ->
                date.id.toString() to date.name
            }
        }
    }

    override fun getGoal(): Result<List<GoalModel>> {
        return restClient.mainApi.getGoal(dataPreference.token)
    }

    override fun getCredits(queries: Map<String, String>): Result<List<CreditStatusModel>> {
        return restClient.mainApi.getCredits(dataPreference.token, queries)
    }

    override fun postCredit(body: CreditBody): Result<CreditModel> {
        return restClient.mainApi.postCredit(dataPreference.token, body)
    }

    override fun getMilkPrice(): Result<MilkPriceModel> {
        return restClient.mainApi.getMilkPrice(dataPreference.token)
    }

    override fun postMilkPrice(body: MilkPriceModel): Result<MilkPriceModel> {
        return restClient.mainApi.postMilkPrice(dataPreference.token, body)
    }

    override fun postMilk(body: AddMilkBody): Result<AddMilkModel> {
        return restClient.mainApi.postMilk(dataPreference.token, body)
    }

    override fun getTotalMilk(): Result<TotalMilkModel> {
        return restClient.mainApi.getTotalMilk(dataPreference.token)
    }

    override fun getCreditDetail(creditId: String): Result<CreditDetailModel> {
        return restClient.mainApi.creditDetail(dataPreference.token, creditId)
    }

    override fun getCreditIssued(queries: Map<String, String>): Result<CreditIssuedModel> {
        return restClient.mainApi.creditIssued(dataPreference.token, queries)
    }

    override fun getIssuedDetail(creditId: String): Result<IssuedDetailModel> {
        return restClient.mainApi.issuedDetail(dataPreference.token, creditId)
    }

    override fun getIssuedGraph(creditId: String): Result<InputStream> {
        return restClient.mainApi.issuedGraph(dataPreference.token, creditId)
            .map { it.byteStream() }
    }
}