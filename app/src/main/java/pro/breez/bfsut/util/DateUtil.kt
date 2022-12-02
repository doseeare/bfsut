package pro.breez.bfsut.util

import android.annotation.SuppressLint
import pro.breez.bfsut.model.DateRange
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {

    fun isToday(date: String): Boolean {
        val currentDate: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        return currentDate == date
    }

    fun reformatDate(date: String?): String? {
        return if (date.isNull()) null
        else {
            val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val mDate = LocalDate.parse(date, format)
            return mDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    fun getToday(): String {
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
    }

    fun toBinaryMonth(date: Int): String {
        val cDate = date.plus(1).toString()
        return if (cDate.length == 1) {
            "0$cDate"
        } else {
            cDate
        }
    }

    fun toBinaryDay(date: Int): String {
        return if (date < 10) {
            "0$date"
        } else {
            "$date"
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun toDateRange(range: androidx.core.util.Pair<Long, Long>): DateRange {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val fromDate = dateFormat.format(Date(range.first))
        val toDate = dateFormat.format(Date(range.second))
        return DateRange(
            start = fromDate,
            end = toDate,
            startMillis = range.first,
            endMillis = range.second
        )
    }

    @SuppressLint("SimpleDateFormat")
    fun toDate(millis: Long): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.format(Date(millis))
    }
}