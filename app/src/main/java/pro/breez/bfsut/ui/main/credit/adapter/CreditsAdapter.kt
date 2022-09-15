package pro.breez.bfsut.ui.main.credit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pro.breez.bfsut.databinding.ItemCreditBinding
import pro.breez.domain.model.output.CreditLogModelOut

class CreditsAdapter(
    private var itemList: List<CreditLogModelOut>,
) :
    RecyclerView.Adapter<CreditsAdapter.SelectorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectorViewHolder {
        val binding =
            ItemCreditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectorViewHolder, position: Int) {
        holder.bind(itemList[position], position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class SelectorViewHolder(private val binding: ItemCreditBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CreditLogModelOut, position: Int) {
            binding.apply {
                nameTv.text = item.customerID
                dateTv.text = item.datePay.toString()
            }
        }
    }
}
