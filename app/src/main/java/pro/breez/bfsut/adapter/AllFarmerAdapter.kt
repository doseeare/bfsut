package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemAllFarmersBinding
import pro.breez.domain.model.output.FarmersModel

class AllFarmerAdapter(itemList: ArrayList<FarmersModel>) :
    BaseRecyclerAdapter<ItemAllFarmersBinding, FarmersModel>(itemList) {

    override fun bind(item: FarmersModel, binding: ItemAllFarmersBinding, position: Int) {
        binding.farmerTv.text = item.full_name
    }

}