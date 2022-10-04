package pro.breez.bfsut.util.alert.dialog

import android.app.Dialog
import android.content.Context
import androidx.annotation.DrawableRes

interface AlertDialogBuilder {

    fun setTitle(title: String): AlertDialogBuilder

    fun setSubTitle(title: String): AlertDialogBuilder

    fun setIcon(@DrawableRes icon: Int): AlertDialogBuilder

    fun create(context: Context): Dialog

    fun setDismissListener(onDismiss : () -> Unit) : AlertDialogBuilder
}