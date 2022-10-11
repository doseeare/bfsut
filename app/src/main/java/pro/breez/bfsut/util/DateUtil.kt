package pro.breez.bfsut.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun isToday(date : String) : Boolean{
        val currentDate: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        return currentDate == date
    }

}