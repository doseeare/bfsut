package pro.breez.bfsut.model

import android.util.Log
import androidx.annotation.DrawableRes
import pro.breez.bfsut.R
import java.io.Serializable

enum class CreditStatusEnum(
    val key: String?,
    @DrawableRes val icon: Int,
    val title: String
) :
    Serializable {
    ALL(null, 0, "Все"),
    SENT("sent", R.drawable.ic_status_all, "Отправлено"),
    COMMIT("committee", R.drawable.ic_status_commit, "Коммитет"),
    ACCEPTED("approved", R.drawable.ic_status_accepted, "Одобрено"),
    DENIED("declined", R.drawable.selector_credit_status_denied, "Отказ");

    companion object {
        fun fromKey(key: String): CreditStatusEnum {
            return values().first { it.key == key }
        }
    }
}