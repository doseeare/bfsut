package pro.breez.bfsut.util.alert

import androidx.annotation.StringRes
import pro.breez.bfsut.R

data class LoadingViewParams(
    val isVisible:Boolean,
    @StringRes val text:Int = R.string.progress_bar_status_text
)