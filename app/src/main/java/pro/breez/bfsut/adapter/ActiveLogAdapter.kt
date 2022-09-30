package pro.breez.bfsut.adapter

import android.util.Log
import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemActiveLogBinding
import pro.breez.domain.model.output.LogsModelOut

class ActiveLogAdapter(
    private val itemList: ArrayList<LogsModelOut>,
    private val itemClicked: (LogsModelOut) -> Unit,
    private val checkBoxChanged: (item: LogsModelOut, isSelected: Boolean) -> Unit,
    private val eachCheckBoxChanged: (areSelected: Boolean) -> Unit
) :
    BaseRecyclerAdapter<ItemActiveLogBinding, LogsModelOut>(itemList) {

    private var select: Boolean = false

    override fun bind(
        item: LogsModelOut,
        binding: ItemActiveLogBinding,
        position: Int
    ) {
        binding.apply {
            dateTv.text = item.date
            dayLitersTv.text = "${item.morning}л"
            nightLitersTv.text = "${item.evening}л"
            nameTv.text = item.farmer_name
            priceTv.text = "${item.overall}с"
            itemList[position].isSelected = select
            checkbox.isChecked = select
            container.setOnClickListener {
                itemClicked.invoke(item)
            }
            checkbox.setOnCheckedChangeListener { compBtn, b ->
                if (compBtn.isPressed) {
                    itemList[position].isSelected = b
                    checkBoxChanged.invoke(item, b)
                    validateCheckBoxes()
                    Log.d("LISTSIZE", "itemList size: ${itemList.size} ")
                    Log.d("LISTSIZE", "itemList: ${itemList} ")
                }
            }
        }
    }

    private fun validateCheckBoxes() {
        var allSelected = false
        var allUnSelected = false

        for (logItem in itemList) {
            if (!logItem.isSelected) {
                allSelected = false
                break
            }
            allSelected = true
        }
        for (logItem in itemList) {
            if (logItem.isSelected) {
                allUnSelected = false
                break
            }
            allUnSelected = true
        }
        if (allSelected) {
            select = false
            eachCheckBoxChanged.invoke(select)
            return
        }
        if (allUnSelected) {
            select = true
            eachCheckBoxChanged.invoke(select)
            return
        }
    }

    fun selectAll(bool: Boolean) {
        select = bool
        notifyDataSetChanged()
    }


}