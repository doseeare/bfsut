package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemFarmerNeedToCheckBinding
import pro.breez.domain.model.output.FarmersCheckModel
import pro.breez.domain.model.output.FarmersModel

class FarmersAdapter(itemList: ArrayList<FarmersModel>) :
    BaseRecyclerAdapter<ItemFarmerNeedToCheckBinding, FarmersModel>(itemList) {

    override fun bind(item: FarmersModel, binding: ItemFarmerNeedToCheckBinding, position: Int) {
        binding.apply {
            binding.farmerTv.text = item.full_name
        }

    }
}
