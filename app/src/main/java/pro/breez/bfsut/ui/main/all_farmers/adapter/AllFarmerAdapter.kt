package pro.breez.bfsut.ui.main.all_farmers.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemAllFarmersBinding

class AllFarmerAdapter(itemList: ArrayList<String>) :
    BaseRecyclerAdapter<ItemAllFarmersBinding, String>(itemList) {

    override fun bind(item: String, binding: ItemAllFarmersBinding) {
        binding.farmerTv.text = item
    }
}