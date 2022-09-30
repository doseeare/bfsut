package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CreditModelIn
import pro.breez.domain.model.output.*
import pro.breez.domain.repository.MainRepository

class MainRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference
) : MainRepository {

    override fun getFarmers(): Result<List<FarmersModelOut>> {
        return restClient.mainApi.getFarmers(dataPreference.token)
    }

    override fun getProduct(id: String): Result<List<ProductsModelOut>> {
        return restClient.mainApi.getProduct(dataPreference.token, id)
    }


    override fun getCategory(): Result<List<CategoryModelOut>> {
        return restClient.mainApi.getCategory(dataPreference.token)
    }

    override fun getDate(): Result<List<Pair<String, String>>> {
        return restClient.mainApi.getDate(dataPreference.token).map {
            it.map { date ->
                date.id.toString() to date.name
            }
        }
    }

    override fun getGoal():  Result<List<GoalModelOut>> {
        return restClient.mainApi.getGoal(dataPreference.token)
    }

    override fun getCredits(): Result<List<CreditLogModelOut>> {
        return restClient.mainApi.getCredits(dataPreference.token)
    }

    override fun postCredit(body: CreditModelIn): Result<CreditModelOut> {
        return restClient.mainApi.postCredit(dataPreference.token, body)
    }
}