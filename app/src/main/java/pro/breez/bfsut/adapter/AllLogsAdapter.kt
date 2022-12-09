package pro.breez.bfsut.adapter

import android.view.View
import androidx.annotation.DrawableRes
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemAllLogBinding
import pro.breez.domain.model.output.LogsModel

class AllLogsAdapter(
    itemList: ArrayList<LogsModel>,
    private val itemClicked: (LogsModel) -> Unit
) :
    BaseRecyclerAdapter<ItemAllLogBinding, LogsModel>(itemList) {

    override fun bind(item: LogsModel, binding: ItemAllLogBinding, position: Int) {
        binding.apply {
            dateTv.text = item.date
            dayLitersTv.text = "${item.morning}л"
            nightLitersTv.text = "${item.evening}л"
            nameTv.text = item.farmer_name
            priceTv.text = "${item.overall}с"

            val clickListener: View.OnClickListener
            val statusTitle: String
            @DrawableRes val statusBg = when (item.status) {
                "active" -> {
                    clickListener = View.OnClickListener { itemClicked.invoke(item) }
                    statusTitle = "активный"
                    R.drawable.bg_shape_status_active
                }
                "paid" -> {
                    clickListener = View.OnClickListener { itemClicked.invoke(item) }
                    statusTitle = "рассчитано"
                    R.drawable.bg_shape_status_paid
                }
                else -> {
                    clickListener = View.OnClickListener {}
                    statusTitle = ""
                    android.R.color.transparent
                }
            }
            binding.root.setOnClickListener(clickListener)
            binding.statusContainer.setBackgroundResource(statusBg)
            binding.statusTv.text = statusTitle
        }
    }
}