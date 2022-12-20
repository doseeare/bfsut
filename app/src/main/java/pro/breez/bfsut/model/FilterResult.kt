package pro.breez.bfsut.model

import androidx.annotation.IdRes
import pro.breez.bfsut.R
import pro.breez.bfsut.util.DateUtil
import pro.breez.domain.model.input.FilterBody
import java.io.Serializable

class FilterResult(
    val farmerName: String? = null,
    val farmerId: String? = null,
    val filterSpan: FilterSpan? = null,
    val range: DateRange? = null
) : Serializable {

    fun toBody() = FilterBody(
        farmerId = farmerId,
        date = filterSpan?.key,
        rangeStart = DateUtil.reformatDate(range?.start),
        rangeEnd = DateUtil.reformatDate(range?.end)
    )
}

class DateRange(
    var start: String?,
    var end: String?,
    var startMillis: Long?,
    var endMillis: Long?,
) : Serializable

enum class FilterSpan(@IdRes val id: Int, val key: String?, val title: String?) : Serializable {
    THIS_WEEK(R.id.this_week_span, "this_week", "За эту неделю"),
    THIS_MONTH(R.id.this_month_span, "this_month", "В этом месяце"),
    LAST_WEEK(R.id.last_week_span, "last_week", "На прошлой неделе"),
    LAST_MONTH(R.id.last_month_span, "last_month", "В прошлом месяце"),
    ALL_TIME(R.id.all_time_span, null, "За все время"),
    NONE(R.id.none_span, null, null);

    companion object {
        fun getFromId(@IdRes id: Int): FilterSpan {
            return values().find { it.id == id }!!
        }
    }

}