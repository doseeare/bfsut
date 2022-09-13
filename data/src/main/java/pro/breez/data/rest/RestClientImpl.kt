package pro.breez.data.rest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.api.AuthApi
import pro.breez.data.utility.adapter.ResultCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RestClientImpl @Inject constructor(
    dataPref: DataPreference,
    baseUrl: String
) : RestClient {

    private val retrofit: Retrofit

    companion object {
        private const val DEFAULT_TIMEOUT_IN_SECONDS: Long = 60
    }

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${dataPref.token}").build()
                )
            }
            .readTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .create()

        this.retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    override val authApi: AuthApi
        get() = retrofit.create(AuthApi::class.java)

}