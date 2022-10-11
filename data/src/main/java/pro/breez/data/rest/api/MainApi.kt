package pro.breez.data.rest.api

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.CalculateActiveBody
import pro.breez.domain.model.input.CreditBody
import pro.breez.domain.model.input.MilkChangesBody
import pro.breez.domain.model.output.*
import retrofit2.http.*

interface MainApi {
    @GET("v1/farmers")
    fun getFarmers(
        @Header("Authorization") token: String,
    ): Result<List<FarmersModel>>

    @POST("v1/mfsys/loan_products")
    fun getProduct(
        @Header("Authorization") token: String,
        @Query("purpose_id") id: String
    ): Result<List<ProductsModel>>

    @GET("v1/mfsys/loan_categories")
    fun getCategory(
        @Header("Authorization") token: String,
    ): Result<List<CategoryModel>>

    //todo не работает, не используется.
    @GET("v1/mfsys/loan_categories")
    fun getDate(
        @Header("Authorization") token: String,
    ): Result<List<CategoryModel>>

    @GET("v1/mfsys/loan_purposes")
    fun getGoal(
        @Header("Authorization") token: String,
    ): Result<List<GoalModel>>

    @GET("v1/credit/")
    fun getCredits(
        @Header("Authorization") token: String,
    ): Result<List<CreditLogModel>>

    @POST("v1/credit/")
    fun postCredit(
        @Header("Authorization") token: String,
        @Body body: CreditBody
    ): Result<CreditModel>

    @GET("v1/journal/")
    fun getActiveLogs(
        @Header("Authorization") token: String,
        @Query("status") status: String
    ): Result<List<LogsModel>>

    @GET("v1/journal/")
    fun getAllLogs(
        @Header("Authorization") token: String,
    ): Result<List<LogsModel>>


    @GET("v1/journal/paid")
    fun getPaidLogs(
        @Header("Authorization") token: String,
    ): Result<List<PaidLogModel>>

    @GET("v1/journal/paid-card")
    fun getPaidLogDetail(
        @Header("Authorization") token: String,
        @Query("receipt_id") id: String
    ): Result<List<PaidLogsDetailModel>>

    @POST("v1/journal/{id}/calculate")
    fun calculateLog(
        @Header("Authorization") token: String,
        @Path("id") status: String
    ): Result<String>

    @POST("v1/journal/calculate_list")
    fun calculateLogs(
        @Header("Authorization") token: String,
        @Body body: CalculateActiveBody
    ): Result<String>

    @PATCH("v1/journal/")
    fun saveMilkChanges(
        @Header("Authorization") token: String,
        @Query("report_id") id: String,
        @Body body: MilkChangesBody
    ): Result<MilkModel>
}