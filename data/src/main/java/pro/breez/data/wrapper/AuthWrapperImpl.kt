package pro.breez.data.auth.wrapper

import android.util.Base64
import android.util.Log
import pro.breez.data.BuildConfig
import javax.inject.Inject

/**
 * Created by azamat on 14/6/21.
 */

/*
class AuthWrapperImpl @Inject constructor(
    private val tokenSharedPreferences: TokenSharedPreferences
):AuthWrapper {
    override fun <T> wrap(onToken: (String) -> T): T {
        val token = "${tokenSharedPreferences.accountToken}:"
        val base64 = Base64.encodeToString(token.toByteArray(), 0).replace("\n", "")
        val readyToken = "Basic $base64"

        if(BuildConfig.DEBUG) {
            Log.d("TOKEN", token)
            Log.d("TOKEN", readyToken)
        }

        return onToken(readyToken)
    }
}


interface AuthWrapper {
    fun <T> wrap(onToken: (String) -> T): T
}*/
