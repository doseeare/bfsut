package pro.breez.bfsut.ui.main.home.adapter

import pro.breez.bfsut.base.GenericRecyclerAdapter
import pro.breez.bfsut.databinding.ItemCreditBinding
import pro.breez.bfsut.databinding.ItemFarmerNeedToCheckBinding
import pro.breez.domain.model.output.CreditLogModelOut
import pro.breez.domain.model.output.FarmersCheckModelOut

class FarmersAdapter(itemList: ArrayList<FarmersCheckModelOut>) :
    GenericRecyclerAdapter<ItemFarmerNeedToCheckBinding, FarmersCheckModelOut>(itemList) {

    override fun bind(item: FarmersCheckModelOut, binding: ItemFarmerNeedToCheckBinding) {
        binding.apply {
            binding.farmerTv.text = item.name
        }
    }
}
