package pro.breez.bfsut.adapter

import android.view.LayoutInflater
import androidx.core.view.isVisible
import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemPaidLogsFarmerBinding
import pro.breez.bfsut.databinding.ItemPaidLogsReceiptBinding
import pro.breez.domain.model.output.PaidLogsByFarmer

class PaidLogsFarmerAdapter(val itemList: List<PaidLogsByFarmer>) :
    BaseRecyclerAdapter<ItemPaidLogsFarmerBinding, PaidLogsByFarmer>(itemList as ArrayList) {

    override fun bind(item: PaidLogsByFarmer, binding: ItemPaidLogsFarmerBinding, position: Int) {
        binding.dateTv.text = "Рассчитано: ${item.paid_date}"
        binding.amountTv.text = "${item.total}с"
        val inflater = LayoutInflater.from(binding.root.context)
        for ((index, log) in item.receipts.withIndex()) {
            ItemPaidLogsReceiptBinding.inflate(inflater).apply {
                dateTv.text = log.date
                dayLitersTv.text = "${log.morning}л"
                nightLitersTv.text = "${log.evening}л"
                priceTv.text = "${log.overall}с"
                if (index == item.receipts.lastIndex)
                    divider.isVisible = false
                binding.logsContainer.addView(this.root)
            }
        }
    }

}