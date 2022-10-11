package pro.breez.bfsut.util.alert.dialog

import android.content.Context
import kotlinx.coroutines.CoroutineScope

interface SelectorDialogBuilder<T> {
    fun showDialog(context: Context)
    fun setVmScope(scope: CoroutineScope)
    fun setList(list: List<T>)
    fun setSearchByVal(value: String)
    fun setResultListener(result: (T) -> Unit)
}