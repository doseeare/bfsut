package pro.breez.bfsut.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import pro.breez.bfsut.R

enum class SnackBarMessageOptions(
    val imageId: Int?,
    val message: String,
    @DrawableRes val backgroundId: Int = R.drawable.ic_notifications_black_24dp,
    @ColorRes val messageTextColor: Int = R.color.white,
    @DrawableRes val closeIcon: Int = R.drawable.ic_home_black_24dp,
    val hideLeftSpace: Boolean = false
) {
/*
    GOODS_IN_CHART(
        R.drawable.ic_cart_success,
        "Товар в корзине!",
        R.drawable.black_snackbar_background,
        R.color.white,
        R.drawable.ic_close_button
    ),*/

}