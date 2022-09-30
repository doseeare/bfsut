package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemAllFarmersBinding

class AllFarmerAdapter(itemList: ArrayList<String>) :
    BaseRecyclerAdapter<ItemAllFarmersBinding, String>(itemList) {

    override fun bind(item: String, binding: ItemAllFarmersBinding, position: Int) {
        binding.farmerTv.text = item
    }

}