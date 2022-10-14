package pro.breez.bfsut.util.alert.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pro.breez.bfsut.adapter.SelectorItemAdapter
import pro.breez.bfsut.databinding.DialogItemSelectorBinding
import pro.breez.bfsut.util.setOnClickOnceListener

class SelectorDialogBuilderImpl<T> : SelectorDialogBuilder<T> {
    private var vmScope: CoroutineScope? = null
    private var list: List<T>? = null
    private var result: ((T) -> Unit)? = null

    private lateinit var searchResult: (List<T>) -> Unit
    private var searchByVal = ""

    override fun showDialog(
        context: Context
    ) {
        Dialog(context).apply {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding = DialogItemSelectorBinding.inflate(inflater)
            setContentView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            checkList(binding, list!!)
            val adapter = SelectorItemAdapter(list!!, searchByVal) {
                binding.selectBtn.isEnabled = true
            }
            setFilter(binding)
            searchResult = {
                checkList(binding, it)
                adapter.update(it)
            }
            binding.itemRv.adapter = adapter
            binding.selectBtn.setOnClickOnceListener {
                result?.invoke(adapter.getSelectedItem())
                dismiss()
            }
            show()
        }
    }

    private fun checkList(binding: DialogItemSelectorBinding, list: List<T>) {
        if (list.isEmpty()) {
            binding.notFoundView.visibility = View.VISIBLE
            binding.itemRv.visibility = View.GONE
            binding.selectBtn.isEnabled = false
        } else {
            binding.notFoundView.visibility = View.GONE
            binding.itemRv.visibility = View.VISIBLE
        }
    }

    private fun setFilter(
        binding: DialogItemSelectorBinding,
    ) {
        binding.search.doOnTextChanged { text, start, before, count ->
            vmScope?.launch {
                val filterResult = filter(searchByVal, text.toString().lowercase())
                searchResult.invoke(filterResult)
            }
        }
    }

    private fun filter(searchByVal: String, searchKey: String): List<T> {
        val arrayList = ArrayList<T>()
        list?.forEach {
            val field = it!!::class.java.getDeclaredField(searchByVal)
            field.isAccessible = true
            val fieldValue = (field.get(it) as String).lowercase()
            field.isAccessible = false
            if (fieldValue.contains(searchKey)) {
                arrayList.add(it)
            }
        }
        return arrayList
    }

    override fun setVmScope(scope: CoroutineScope) {
        vmScope = scope
    }

    override fun setList(list: List<T>) {
        this.list = list
    }

    override fun setSearchByVal(value: String) {
        searchByVal = value
    }

    override fun setResultListener(result: (T) -> Unit) {
        this.result = result
    }

}