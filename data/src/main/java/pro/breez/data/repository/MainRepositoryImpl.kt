package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.AddMilkBody
import pro.breez.domain.model.input.CreditBody
import pro.breez.domain.model.output.*
import pro.breez.domain.repository.MainRepository

class MainRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference
) : MainRepository {

    override fun getFarmers(): Result<List<FarmersModel>> {
        return restClient.mainApi.getFarmers(dataPreference.token)
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

    override fun getCredits(): Result<List<CreditLogModel>> {
        return restClient.mainApi.getCredits(dataPreference.token)
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
        return  restClient.mainApi.postMilk(dataPreference.token, body)
    }
}