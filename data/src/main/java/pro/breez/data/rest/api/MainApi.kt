package pro.breez.data.rest.api

import okhttp3.ResponseBody
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.*
import pro.breez.domain.model.output.*
import retrofit2.http.*

interface MainApi {

    @GET("v1/farmers")
    fun getFarmers(
        @Header("Authorization") token: String,
    ): Result<List<FarmersModel>>

    @GET("v1/agents/username")
    fun getUserName(
        @Header("Authorization") token: String,
    ): Result<UserNameModel>

    @GET("v1/farmers/profile")
    fun getFarmerProfile(
        @Header("Authorization") token: String,
        @Query("farmer_id") id: String,
    ): Result<FarmerProfileModel>

    @GET("v1/farmers/farmer-list")
    fun getFarmersCheck(
        @Header("Authorization") token: String,
    ): Result<List<FarmerCheckModel>>

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
        @QueryMap queries: Map<String, String>
    ): Result<List<CreditStatusModel>>

    @POST("v1/credit/")
    fun postCredit(
        @Header("Authorization") token: String,
        @Body body: CreditBody
    ): Result<CreditModel>

    @GET("v1/journal/")
    fun getActiveLogs(
        @Header("Authorization") token: String,
        @QueryMap queries: Map<String, String>
    ): Result<List<LogsModel>>

    @GET("v1/journal/")
    fun getAllLogs(
        @Header("Authorization") token: String,
        @QueryMap queries: Map<String, String>
    ): Result<List<LogsModel>>


    @GET("v1/journal/paid")
    fun getPaidLogs(
        @Header("Authorization") token: String,
        @QueryMap queries: Map<String, String>
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
    ): Result<CalculateModel>

    @POST("v1/journal/calculate_list")
    fun calculateLogs(
        @Header("Authorization") token: String,
        @Body body: CalculateActiveBody
    ): Result<CalculateModel>

    @PATCH("v1/journal/")
    fun saveMilkChanges(
        @Header("Authorization") token: String,
        @Query("report_id") id: String,
        @Body body: MilkChangesBody
    ): Result<MilkModel>

    @GET("v1/mfsys/nationality")
    fun getNationality(
        @Header("Authorization") token: String,
    ): Result<List<MfSysModel>>

    @GET("v1/mfsys/document_types")
    fun getDocTypes(
        @Header("Authorization") token: String,
    ): Result<List<MfSysModel>>

    @GET("v1/mfsys/document_issue")
    fun getDocIssue(
        @Header("Authorization") token: String,
    ): Result<List<MfSysModel>>

    @GET("v1/mfsys/countries")
    fun getArea(
        @Header("Authorization") token: String,
    ): Result<List<MfSysModel>>

    @GET("v1/mfsys/states")
    fun getCountries(
        @Header("Authorization") token: String,
    ): Result<List<MfSysModel>>

    @GET("v1/mfsys/regions")
    fun getRegions(
        @Header("Authorization") token: String,
        @Query("country_id") area_id: String
    ): Result<List<MfSysModel>>

    @GET("v1/mfsys/educations")
    fun getEducations(
        @Header("Authorization") token: String,
    ): Result<List<MfSysModel>>

    @GET("v1/mfsys/purposes")
    fun getJobPurpose(
        @Header("Authorization") token: String,
    ): Result<List<MfSysModel>>

    @GET("v1/agents/actual_milk_price")
    fun getMilkPrice(
        @Header("Authorization") token: String,
    ): Result<MilkPriceModel>

    @PATCH("v1/agents/actual_milk_price")
    fun postMilkPrice(
        @Header("Authorization") token: String,
        @Body body: MilkPriceModel
    ): Result<MilkPriceModel>

    @POST("v1/journal/")
    fun postMilk(
        @Header("Authorization") token: String,
        @Body body: AddMilkBody
    ): Result<AddMilkModel>

    @GET("v1/journal/total-day")
    fun getTotalMilk(
        @Header("Authorization") token: String,
    ): Result<TotalMilkModel>

    @POST("v1/farmers/")
    fun postFarmer(
        @Header("Authorization") token: String,
        @Body body: FarmerBody
    ): Result<DefaultSuccessModel>

    @PATCH("v1/farmers/profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body body: FarmerBody,
        @Query("farmer_id") id: String
    ): Result<DefaultSuccessModel>

    @POST("v1/farmers/search")
    fun searchFarmer(
        @Header("Authorization") token: String,
        @QueryMap queries: Map<String, String>
    ): Result<List<MfSysFarmerModel>>

    @GET("v1/credit/search")
    fun creditSearchFarmer(
        @Header("Authorization") token: String,
        @QueryMap queries: Map<String, String>
    ): Result<List<CreditSearchFarmerModel>>

    @POST("v1/credit/detailed")
    fun creditDetail(
        @Header("Authorization") token: String,
        @Query("credit_id") creditId: String
    ): Result<CreditDetailModel>

    @POST("v1/credit/issued")
    fun issuedDetail(
        @Header("Authorization") token: String,
        @Query("credit_id") creditId: String
    ): Result<IssuedDetailModel>

    @GET("v1/credit/schedule-download")
    @Streaming
    fun issuedGraph(
        @Header("Authorization") token: String,
        @Query("credit_id") creditId: String
    ): Result<ResponseBody>

    @GET("v1/credit/issued")
    fun creditIssued(
        @Header("Authorization") token: String,
        @QueryMap queries: Map<String, String>
    ): Result<CreditIssuedModel>

    @GET("v1/journal/evening-fill-status")
    fun getEveningStatus(
        @Header("Authorization") token: String,
    ): Result<EveningModel>

    @DELETE("v1/journal/delete/{id}")
    fun deleteLog(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Result<DefaultSuccessModel>


    @GET("v1/credit/unread-credit-amount")
    fun getUnreadCreditCount(
        @Header("Authorization") token: String,
    ): Result<UnreadCountModel>

}