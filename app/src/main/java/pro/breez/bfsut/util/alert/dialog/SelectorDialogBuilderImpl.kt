package pro.breez.bfsut.util.alert.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import pro.breez.bfsut.databinding.DialogItemSelectorBinding
import pro.breez.bfsut.ui.main.add_credit.adapter.SelectorItemAdapter

class SelectorDialogBuilderImpl(
    private val list: List<Pair<String, String>>,
    private val result: (Pair<String, String>) -> Unit
) : SelectorDialogBuilder {

    override fun showDialog(
        context: Context
    ) {
        Dialog(context).apply {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val binding = DialogItemSelectorBinding.inflate(inflater)
            setContentView(binding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            val adapter = SelectorItemAdapter(list) {
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