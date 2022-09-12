package pro.breez.mobimarket.utility.alert.date

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.util.*

/**
 * Created by azamat on 9/6/21.
 */

class DatePickerDialogBuilder : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var onSet:((Date) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = GregorianCalendar(year, month, dayOfMonth).time
        onSet?.invoke(date)
    }

    fun setOnPositiveClickedListener(onSet:((Date) -> Unit)) {
        this.onSet = onSet
    }

    fun show(fragmentManager:FragmentManager, onSet:(Date) -> Unit) {
        show(fragmentManager, "datePicker")
        this.onSet = onSet
    }
}