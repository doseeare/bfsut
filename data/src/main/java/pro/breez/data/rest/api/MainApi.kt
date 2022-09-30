package pro.breez.data.rest.api

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CreditModelIn
import pro.breez.domain.model.output.*
import retrofit2.http.*

interface MainApi {
    @GET("v1/farmers")
    fun getFarmers(
        @Header("Authorization") token: String,
    ): Result<List<FarmersModelOut>>

    @POST("v1/mfsys/loan_products")
    fun getProduct(
        @Header("Authorization") token: String,
        @Query("purpose_id") id: String
    ): Result<List<ProductsModelOut>>

    @GET("v1/mfsys/loan_categories")
    fun getCategory(
        @Header("Authorization") token: String,
    ): Result<List<CategoryModelOut>>

    //todo не работает, не используется.
    @GET("v1/mfsys/loan_categories")
    fun getDate(
        @Header("Authorization") token: String,
    ): Result<List<CategoryModelOut>>

    @GET("v1/mfsys/loan_purposes")
    fun getGoal(
        @Header("Authorization") token: String,
    ): Result<List<GoalModelOut>>

    @GET("v1/credit/")
    fun getCredits(
        @Header("Authorization") token: String,
    ): Result<List<CreditLogModelOut>>

    @POST("v1/credit/")
    fun postCredit(
        @Header("Authorization") token: String,
        @Body body: CreditModelIn
    ): Result<CreditModelOut>

    @GET("v1/journal/")
    fun getActiveLogs(
        @Header("Authorization") token: String,
        @Query("status") status: String
    ): Result<List<LogsModelOut>>


}