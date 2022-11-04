package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemFarmerNeedToCheckBinding
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.domain.model.output.FarmerCheckModel

class FarmersAdapter(
    itemList: ArrayList<FarmerCheckModel>,
    private val itemClicked: (FarmerCheckModel) -> Unit,
    private val addClicked: (FarmerCheckModel) -> Unit
) :
    BaseRecyclerAdapter<ItemFarmerNeedToCheckBinding, FarmerCheckModel>(itemList) {

    override fun bind(
        item: FarmerCheckModel,
        binding: ItemFarmerNeedToCheckBinding,
        position: Int
    ) {
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
