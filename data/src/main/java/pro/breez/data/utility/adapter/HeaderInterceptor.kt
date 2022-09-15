package pro.breez.data.utility.adapter

import okhttp3.Interceptor
import okhttp3.Response
import pro.breez.data.cache.DataPreference

class HeaderInterceptor(val dataPreference: DataPreference) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${dataPreference.token}")
                .build()
        )
    }
}