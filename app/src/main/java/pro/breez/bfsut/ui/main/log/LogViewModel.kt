package pro.breez.bfsut.ui.main.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pro.breez.bfsut.base.BaseViewModel

class LogViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}