package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemCreditBinding
import pro.breez.domain.model.output.CreditLogModelOut

class CreditsAdapter(itemList: ArrayList<CreditLogModelOut>) :
    BaseRecyclerAdapter<ItemCreditBinding, CreditLogModelOut>(itemList) {

    override fun bind(item: CreditLogModelOut, binding: ItemCreditBinding, position: Int) {
        binding.apply {
            nameTv.text = item.customerID
            dateTv.text = item.datePay.toString()
        }
    }
}
