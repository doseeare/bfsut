package pro.breez.bfsut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.ItemSelectorBinding
import pro.breez.bfsut.util.setOnClickOnceListener

class SelectorItemAdapter<T>(
    private var itemList: List<T>,
    val valueName: Array<String>,
    private val activeBtn: () -> Unit
) :
    RecyclerView.Adapter<SelectorItemAdapter<T>.SelectorViewHolder>() {
    private var lastCheckedPos = -1
    private var selectedItem: T? = null
        set(value) {
            field = value
            activeBtn.invoke()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectorViewHolder {
        val binding =
            ItemSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectorViewHolder, position: Int) {
        holder.bind(itemList[position], position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun update(list: List<T>) {
        this.itemList = list
        notifyDataSetChanged()
    }

    fun getSelectedItem() = selectedItem!!

    inner class SelectorViewHolder(private val binding: ItemSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T, position: Int) {
            binding.apply {
                val strBuilder = StringBuilder()
                valueName.forEach {
                    val value = item!!::class.java.getDeclaredField(it)
                    value.isAccessible = true
                    value.get(item)?.let {
                        strBuilder.append("${it as String?}")
                    }
                    value.isAccessible = false
                }
                nameTv.text = strBuilder.toString()
                setSelect(lastCheckedPos == position, position)
                root.setOnClickOnceListener {
                    val copyLastPos = lastCheckedPos
                    lastCheckedPos = position
                    notifyItemChanged(position)
                    notifyItemChanged(copyLastPos)
                }
            }
        }

        private fun setSelect(isSelected: Boolean, position: Int) {
            if (isSelected) {
                selectedItem = itemList[position]
            }
            binding.indicatorImg.isVisible = isSelected
        }
    }
}
