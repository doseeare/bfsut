package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemPaidLogsBinding
import pro.breez.domain.model.output.PaidLogModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PaidLogsAdapter(
    itemList: ArrayList<PaidLogModel>,
    private val itemClicked: (PaidLogModel) -> Unit
) :
    BaseRecyclerAdapter<ItemPaidLogsBinding, PaidLogModel>(itemList) {

    override fun bind(item: PaidLogModel, binding: ItemPaidLogsBinding, position: Int) {
        val currentDate: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val dateTitle =
            if (currentDate == item.paid_date) "Рассчитано на сегодня" else item.paid_date
        binding.dateTv.text = dateTitle
        binding.litersTv.text = "${item.total_milk} л"
        binding.totalPriceTv.text = "${item.total_sum} с"
        binding.root.setOnClickListener {
            itemClicked.invoke(item)
        }
    }
}