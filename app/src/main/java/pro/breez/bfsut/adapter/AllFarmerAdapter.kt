package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemAllFarmersBinding
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.bfsut.util.visibility
import pro.breez.domain.model.output.FarmersModel

class AllFarmerAdapter(val itemList: ArrayList<FarmersModel>, val block: (FarmersModel) -> Unit) :
    BaseRecyclerAdapter<ItemAllFarmersBinding, FarmersModel>(itemList) {

    override fun bind(item: FarmersModel, binding: ItemAllFarmersBinding, position: Int) {
        binding.farmerTv.text = item.full_name
        if (position == itemList.lastIndex) {
            binding.divider.visibility(false)
        }
        binding.root.setOnClickOnceListener {
            block.invoke(item)
        }
    }

}