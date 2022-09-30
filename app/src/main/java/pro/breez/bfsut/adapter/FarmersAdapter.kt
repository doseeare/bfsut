package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemFarmerNeedToCheckBinding
import pro.breez.domain.model.output.FarmersCheckModelOut

class FarmersAdapter(itemList: ArrayList<FarmersCheckModelOut>) :
    BaseRecyclerAdapter<ItemFarmerNeedToCheckBinding, FarmersCheckModelOut>(itemList) {

    override fun bind(item: FarmersCheckModelOut, binding: ItemFarmerNeedToCheckBinding, position: Int) {
        binding.apply {
            binding.farmerTv.text = item.name
        }
    }
}
