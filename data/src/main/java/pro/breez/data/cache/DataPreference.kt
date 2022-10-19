package pro.breez.data.cache

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataPreference @Inject constructor(@ApplicationContext context: Context) {

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

    var token: String
        get() = preferences.getString("token", "")!!
        set(value) = preferences.edit {
            it.putString("token", value)
        }
}