package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CreditModelIn
import pro.breez.domain.model.output.CreditLogModelOut
import pro.breez.domain.model.output.CreditModelOut

interface MainRepository {
    fun getFarmers(): Result<List<Pair<String, String>>>
    fun getProduct(): Result<List<Pair<String, String>>>
    fun getCategory(): Result<List<Pair<String, String>>>
    fun getDate(): Result<List<Pair<String, String>>>
    fun getGoal(): Result<List<Pair<String, String>>>
    fun getCredits(): Result<List<CreditLogModelOut>>
    fun postCredit(body : CreditModelIn): Result<CreditModelOut>
}