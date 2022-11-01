package pro.breez.data.cache

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsPreference @Inject constructor(@ApplicationContext context: Context) {

    private val NAME = "Bfsut.Data.Pref"
    private val MODE = Context.MODE_PRIVATE

    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var lastPriceChangeDate: String
        get() = preferences.getString("lastPriceChangeDate", "")!!
        set(value) = preferences.edit {
            it.putString("lastPriceChangeDate", value)
        }

    var lastManualPriceChangeDate: String
        get() = preferences.getString("lastManualPriceChangeDate", "")!!
        set(value) = preferences.edit {
            it.putString("lastManualPriceChangeDate", value)
        }

}