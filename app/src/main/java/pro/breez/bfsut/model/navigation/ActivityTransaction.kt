package pro.breez.bfsut.model.navigation

import android.os.Bundle
import androidx.annotation.IdRes

data class ActivityTransaction(
    @IdRes val navigationId: Int,
    val replace: Boolean = true,
    val bundle: Bundle? = null
)
