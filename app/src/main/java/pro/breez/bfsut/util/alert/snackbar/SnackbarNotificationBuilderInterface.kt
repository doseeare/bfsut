package pro.breez.bfsut.util.alert.snackbar

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import com.google.android.material.snackbar.Snackbar
import pro.breez.bfsut.model.SnackBarMessageOptions

interface SnackbarNotificationBuilderInterface {
    fun setBackground(@DrawableRes resourceId: Int): SnackbarNotificationBuilderInterface
    fun setMessage(value: String): SnackbarNotificationBuilderInterface
    fun setMessageColor(@ColorRes colorId: Int): SnackbarNotificationBuilderInterface
    fun setLeftIcon(@DrawableRes resourceId: Int): SnackbarNotificationBuilderInterface
    fun setCloseIcon(@DrawableRes resourceId: Int): SnackbarNotificationBuilderInterface
    fun setShowLength(
        @IntRange(
            from = -2,
            to = 0
        ) length: Int
    ): SnackbarNotificationBuilderInterface

    fun hideLeftIconPadding(): SnackbarNotificationBuilderInterface
    fun fromOptions(options: SnackBarMessageOptions): SnackbarNotificationBuilderInterface
    fun create(container: View): Snackbar
}
