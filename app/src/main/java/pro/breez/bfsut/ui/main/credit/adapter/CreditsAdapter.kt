package pro.breez.bfsut.ui.main.credit.adapter

import pro.breez.bfsut.base.GenericRecyclerAdapter
import pro.breez.bfsut.databinding.ItemCreditBinding
import pro.breez.domain.model.output.CreditLogModelOut

class CreditsAdapter(itemList: ArrayList<CreditLogModelOut>) :
    GenericRecyclerAdapter<ItemCreditBinding, CreditLogModelOut>(itemList) {

    override fun bind(item: CreditLogModelOut, binding: ItemCreditBinding) {
        binding.apply {
            nameTv.text = item.customerID
            dateTv.text = item.datePay.toString()
        }
    }
}
