package pro.breez.bfsut.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import pro.breez.bfsut.R

enum class SnackBarMessageOptions(
    val imageId: Int? = null,
    val message: String,
    @DrawableRes val backgroundId: Int = R.drawable.ic_notifications_black_24dp,
    @ColorRes val messageTextColor: Int = R.color.white,
    @DrawableRes val closeIcon: Int? = R.drawable.ic_default_error,
    val hideLeftSpace: Boolean = false
) {

    AUTH_FIELDS_ERROR(
        message = "Заполните все поля",
        backgroundId = R.drawable.bg_error_shape,
        messageTextColor = R.color.white,
        closeIcon = null
    ),

}