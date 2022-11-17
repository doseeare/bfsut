package pro.breez.bfsut.adapter

import pro.breez.bfsut.base.BaseRecyclerAdapter
import pro.breez.bfsut.databinding.ItemActiveLogBinding
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.domain.model.output.LogsModel

class ActiveLogAdapter(
    private val itemList: ArrayList<LogsModel>,
    private val itemClicked: (LogsModel) -> Unit,
    private val checkBoxChanged: (item: LogsModel, isSelected: Boolean) -> Unit,
    private val eachCheckBoxChanged: (areSelected: Boolean) -> Unit
) :
    BaseRecyclerAdapter<ItemActiveLogBinding, LogsModel>(itemList) {

    private var select: Boolean = false

    override fun bind(
        item: LogsModel,
        binding: ItemActiveLogBinding,
        position: Int
    ) {
        binding.apply {
            dateTv.text = item.date
            dayLitersTv.text = "${item.morning}л"
            nightLitersTv.text = "${item.evening}л"
            nameTv.text = item.farmer_name
            priceTv.text = "${item.overall}с"
            checkbox.isChecked = select
            container.setOnClickOnceListener {
                itemClicked.invoke(item)
            }
            checkbox.setOnCheckedChangeListener { compBtn, b ->
                if (compBtn.isPressed) {
                    itemList[position].isSelected = b
                    checkBoxChanged.invoke(item, b)
                    validateCheckBoxes()
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