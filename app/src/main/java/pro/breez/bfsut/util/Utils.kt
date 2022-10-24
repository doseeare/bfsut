package pro.breez.bfsut.util

import android.text.InputType
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redmadrobot.inputmask.MaskedTextChangedListener


object Utils {

    fun EditText.setNumberMask() {
        inputType = InputType.TYPE_CLASS_PHONE
        val mask = MaskedTextChangedListener("+996 [000] [000] [000]", this)
        this.addTextChangedListener(mask)
    }


}