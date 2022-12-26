package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemPaidLogsBinding
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.domain.model.output.PaidLogModel

class PaidLogsAdapter(
    itemList: ArrayList<PaidLogModel>,
    private val itemClicked: (PaidLogModel) -> Unit
) :
    BaseRecyclerAdapter<ItemPaidLogsBinding, PaidLogModel>(itemList) {

    override fun bind(item: PaidLogModel, binding: ItemPaidLogsBinding, position: Int) {
        val dateTitle =
            if (DateUtil.isToday(item.paid_date)) "Рассчитано на сегодня" else "Рассчитано за ${item.paid_date}"
        binding.dateTv.text = dateTitle
        binding.litersTv.text = "${item.total_milk}л"
        binding.totalPriceTv.text = "${item.total_sum}с"
        binding.root.setOnClickOnceListener {
            itemClicked.invoke(item)
        }
    }
}