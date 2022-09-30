package pro.breez.bfsut.util.alert.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import pro.breez.bfsut.adapter.SelectorItemAdapter
import pro.breez.bfsut.databinding.DialogItemSelectorBinding

class SelectorDialogBuilderImpl<T>(
    private val list: List<T>,
    private val textFiledList: List<String>,
    private val result: (T) -> Unit
) : SelectorDialogBuilder {

    override fun showDialog(
        context: Context
    ) {
        Dialog(context).apply {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding = DialogItemSelectorBinding.inflate(inflater)
            setContentView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val adapter = SelectorItemAdapter<T>(list, textFiledList) {
                binding.selectBtn.isEnabled = true
            }
            binding.itemRv.adapter = adapter
            binding.selectBtn.setOnClickListener {
                result.invoke(adapter.getSelectedItem())
                dismiss()
            }
            show()
        }
    }
}