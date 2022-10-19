package pro.breez.data.utility.adapter

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import pro.breez.data.cache.DataPreference
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dataPreference: DataPreference
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {
            val localBroadcastManager = LocalBroadcastManager.getInstance(context)
            val intent = Intent(LOG_OUT)
            dataPreference.token = ""
            localBroadcastManager.sendBroadcast(intent)
            return null
        }
        return response.request.newBuilder().build()
    }

    companion object {
        const val LOG_OUT = "LOG_OUT_KEY"
    }
}