package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemFarmerNeedToCheckBinding
import pro.breez.domain.model.output.FarmersCheckModel

class FarmersAdapter(itemList: ArrayList<FarmersCheckModel>) :
    BaseRecyclerAdapter<ItemFarmerNeedToCheckBinding, FarmersCheckModel>(itemList) {

    override fun bind(item: FarmersCheckModel, binding: ItemFarmerNeedToCheckBinding, position: Int) {
        binding.apply {
            binding.farmerTv.text = item.name
        }
    }
}
