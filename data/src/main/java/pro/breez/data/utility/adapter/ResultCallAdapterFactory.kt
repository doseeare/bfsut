package pro.breez.data.utility.adapter

import com.google.gson.Gson
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import pro.breez.data.entity.response.ErrorResponse
import pro.breez.data.entity.response.ErrorsResponse
import pro.breez.data.exception.HttpException
import pro.breez.data.exception.UnauthorizedError
import pro.breez.domain.exception.ConnectionLostException
import pro.breez.domain.interactor.base.Result
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory private constructor() : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() = ResultCallAdapterFactory()

        private val client = OkHttpClient()
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Result::class.java != getRawType(returnType)) {
            return null
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Result return type must be parameterized as Result<Foo> or Result<out Foo>"
            )
        }
        val responseType = getParameterUpperBound(0, returnType)

        return ResultCallAdapter<Any>(responseType)
    }

    private class ResultCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Result<T>> {

        override fun responseType(): Type = responseType

        override fun adapt(call: Call<T>): Result<T> {
            val urlBuilder = "https://clients1.google.com/generate_204".toHttpUrlOrNull()!!
            val request = Request.Builder()
            request.url(urlBuilder)
            try {
                val googleResponse = client.newCall(request.build()).execute()
                return if (googleResponse.isSuccessful) {
                    try {
                        val response = call.execute()
                        when (response.isSuccessful) {
                            true -> {
                                val result =
                                    Result.Success(response.body()!!)
                                result.networkConnectionEnable = true
                                result
                            }
                            false -> {
                                val stream = response.errorBody()?.string()
                                val json = JSONObject(stream ?: "")
                                if (json.get("data") is JSONArray) {
                                    val error = Gson().fromJson(
                                        stream,
                                        ErrorsResponse::class.java
                                    )
                                    Result.Exception(
                                        HttpException(
                                            response.code(),
                                            error.data[0].message
                                        )
                                    )
                                } else {
                                    val error = Gson().fromJson(
                                        stream,
                                        ErrorResponse::class.java
                                    )

                                    if (response.code() == 401) {
                                        Result.Exception(
                                            UnauthorizedError(
                                                response.code(),
                                                error.data.message
                                            )
                                        )
                                    } else
                                        Result.Exception(
                                            HttpException(
                                                response.code(),
                                                error.data.message
                                            )
                                        )
                                }
                            }
                        }
                    } catch (throwable: Throwable) {
                        throwable.printStackTrace()
                        Result.Exception(throwable)
                    }
                } else {
                    Result.Exception(ConnectionLostException())
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                return Result.Exception(ConnectionLostException())
            }
        }
    }
}