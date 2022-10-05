package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemCreditBinding
import pro.breez.domain.model.output.CreditLogModel

class CreditsAdapter(itemList: ArrayList<CreditLogModel>) :
    BaseRecyclerAdapter<ItemCreditBinding, CreditLogModel>(itemList) {

    override fun bind(item: CreditLogModel, binding: ItemCreditBinding, position: Int) {
        binding.apply {
            nameTv.text = item.customerID
            dateTv.text = item.datePay.toString()
        }
    }
}
