package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CreditModelIn
import pro.breez.domain.model.output.CreditLogModelOut
import pro.breez.domain.model.output.CreditModelOut
import pro.breez.domain.repository.MainRepository

class MainRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference
) : MainRepository {

    override fun getFarmers(): Result<List<Pair<String, String>>> {
        return restClient.mainApi.getFarmers(dataPreference.token).map {
            it.map { farmer ->
                farmer.id to "${farmer.first_name} ${farmer.father_name} ${farmer.last_name}"
            }
        }
    }

    override fun getProduct(id: String): Result<List<Pair<String, String>>> {
        return restClient.mainApi.getProduct(dataPreference.token, id).map {
            it.map { product ->
                product.id.toString() to product.name
            }
        }
    }

    override fun getCategory(): Result<List<Pair<String, String>>> {
        return restClient.mainApi.getCategory(dataPreference.token).map {
            it.map { category ->
                category.id.toString() to category.name
            }
        }
    }

    override fun getDate(): Result<List<Pair<String, String>>> {
        return restClient.mainApi.getDate(dataPreference.token).map {
            it.map { date ->
                date.id.toString() to date.name
            }
        }
    }

    override fun getGoal(): Result<List<Pair<String, String>>> {
        return restClient.mainApi.getGoal(dataPreference.token).map {
            it.map { goal ->
                goal.mfsys_id to goal.name
            }
        }
    }

    override fun getCredits(): Result<List<CreditLogModelOut>> {
        return restClient.mainApi.getCredits(dataPreference.token)
    }

    override fun postCredit(body: CreditModelIn): Result<CreditModelOut> {
        return restClient.mainApi.postCredit(dataPreference.token, body)
    }
}