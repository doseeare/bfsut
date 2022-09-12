package pro.breez.data.cache

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class ApplicationSettingsPreferences @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        const val PREFERENCES_FILE_NAME = "pro.breez.data.application.settings"

        const val SETTINGS_CANCELED_DEFAULT_VALUE = false
        const val SETTINGS_NOTIFICATION_ENABLE_REQUEST_DATE_DEFAULT_VALUE = 0L
        const val APPLICATION_UPDATE_REQUEST_DATE_DEFAULT_VALUE = 0L

        const val NOTIFICATION_SETTINGS_CANCELED = "pro.breez.data.notification.canceled"
        const val NOTIFICATION_ENABLE_REQUEST_DATE = "pro.breez.data.notification.last.request"
        const val APPLICATION_UPDATE_REQUEST_DATE = "pro.breez.data.application.update.date"
    }

    private val pref = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    val applicationUpdateRequestHasExpired: Boolean
        get() {
            val date = pref.getLong(
                APPLICATION_UPDATE_REQUEST_DATE,
                APPLICATION_UPDATE_REQUEST_DATE_DEFAULT_VALUE
            )
            val newDate = Date().time + 3 * 24 * 60 * 60 * 1000
            pref.edit().putLong(APPLICATION_UPDATE_REQUEST_DATE, newDate).apply()
            return date <= Date().time
        }

    var notificationSettingsApplied: Boolean
        get() = pref.getBoolean(NOTIFICATION_SETTINGS_CANCELED, SETTINGS_CANCELED_DEFAULT_VALUE)
        set(value) {
            pref.edit().putBoolean(NOTIFICATION_SETTINGS_CANCELED, value).apply()
        }

    var notificationEnableRequestDate: Date
        get() {
            val dateAsLong = pref.getLong(
                NOTIFICATION_ENABLE_REQUEST_DATE,
                SETTINGS_NOTIFICATION_ENABLE_REQUEST_DATE_DEFAULT_VALUE
            )
            return Date(dateAsLong)
        }
        set(value) {
            pref.edit().putLong(NOTIFICATION_ENABLE_REQUEST_DATE, value.time).apply()
        }
}