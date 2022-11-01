package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemFarmerNeedToCheckBinding
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.domain.model.output.FarmersModel

class FarmersAdapter(
    itemList: ArrayList<FarmersModel>,
    private val itemClicked: (FarmersModel) -> Unit,
    private val addClicked: (FarmersModel) -> Unit
) :
    BaseRecyclerAdapter<ItemFarmerNeedToCheckBinding, FarmersModel>(itemList) {

    override fun bind(item: FarmersModel, binding: ItemFarmerNeedToCheckBinding, position: Int) {
        binding.apply {
            binding.itemView.setOnClickOnceListener {
                itemClicked.invoke(item)
            }
            binding.buttonView.setOnClickOnceListener {
                addClicked.invoke(item)
            }
            binding.farmerTv.text = item.full_name
        }

    }
}
