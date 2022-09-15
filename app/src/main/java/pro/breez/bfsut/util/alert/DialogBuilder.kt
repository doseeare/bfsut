package pro.breez.bfsut.util.alert

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog



interface DialogBuilder {
    fun setTitle(@StringRes title: Int): DialogBuilder
    fun setMessage(@StringRes message: Int): DialogBuilder
    fun setCancelable(cancelable:Boolean): DialogBuilder
    fun create(context: Context): AlertDialog
}