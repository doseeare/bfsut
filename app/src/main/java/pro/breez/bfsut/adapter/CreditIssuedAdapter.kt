package pro.breez.bfsut.adapter

import androidx.core.content.ContextCompat
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemCreditIssuedBinding
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.ifNotNull
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.domain.model.output.IssuedData

class CreditIssuedAdapter(
    itemList: List<IssuedData>,
    private val itemClicked: (IssuedData) -> Unit
) :
    BaseRecyclerAdapter<ItemCreditIssuedBinding, IssuedData>(itemList as ArrayList<IssuedData>) {

    override fun bind(item: IssuedData, binding: ItemCreditIssuedBinding, position: Int) {
        binding.apply {
            nameTv.text = item.full_name
            amountPerMonthTv.text = "${item.monthly_payment_amount} с/мес."
            amountTv.text = "${item.amount} c"

            item.is_overdue.ifNotNull {
                if (item.is_overdue!!) {
                    leftBottomTv.text = "${item.overdue} дней"
                    leftBottomTv.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.error_color
                        )
                    )
                    leftTopTv.text = "Просрочено:"
                } else {
                    leftBottomTv.text = DateUtil.reformat(item.date)
                    leftTopTv.text = "Выплата:"
                }
            }
            item.date_disburse_plan.ifNotNull {
                binding.leftTopTv.text = "Дата выдачи"
                binding.leftBottomTv.text = it
            }
            root.setOnClickOnceListener {
                itemClicked.invoke(item)
            }
        }
    }
}
