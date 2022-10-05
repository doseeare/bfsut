package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemPaidLogsDetailBinding
import pro.breez.domain.model.output.PaidLogsDetailModel

class PaidLogDetailAdapter(
    itemList: ArrayList<PaidLogsDetailModel>,
) :
    BaseRecyclerAdapter<ItemPaidLogsDetailBinding, PaidLogsDetailModel>(itemList) {

    override fun bind(
        item: PaidLogsDetailModel,
        binding: ItemPaidLogsDetailBinding,
        position: Int
    ) {
        binding.apply {
            dateTv.text = item.date
            dayLitersTv.text = "${item.morning}л"
            nightLitersTv.text = "${item.evening}л"
            nameTv.text = item.farmer_name
            priceTv.text = "${item.overall}с"
        }
    }
}