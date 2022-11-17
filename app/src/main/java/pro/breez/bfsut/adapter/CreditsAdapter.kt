package pro.breez.bfsut.adapter

import android.view.View
import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemCreditStatusBinding
import pro.breez.bfsut.model.CreditStatusEnum
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.domain.model.output.CreditStatusModel

class CreditsAdapter(
    itemList: List<CreditStatusModel>,
    private val isAll: Boolean,
    private val itemClicked: (String) -> Unit
) :
    BaseRecyclerAdapter<ItemCreditStatusBinding, CreditStatusModel>(itemList as ArrayList<CreditStatusModel>) {

    override fun bind(item: CreditStatusModel, binding: ItemCreditStatusBinding, position: Int) {
        binding.apply {
            nameTv.text = item.full_name
            dateTv.text = item.credit_request_creation_date

            val creditStatus = CreditStatusEnum.fromKey(item.status)
            if (isAll) {
                binding.statusTv.visibility = View.VISIBLE
                binding.statusTv.text = creditStatus.title
                binding.statusTv.setCompoundDrawablesWithIntrinsicBounds(creditStatus.icon, 0, 0, 0)
            } else {
                binding.statusImg.visibility = View.VISIBLE
                binding.statusImg.setImageResource(creditStatus.icon)
            }
            binding.root.setOnClickOnceListener {
                itemClicked.invoke(item.id)
            }
        }
    }
}
