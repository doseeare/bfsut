package pro.breez.bfsut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.ItemSelectorBinding

class SelectorItemAdapter<T>(
    private var itemList: List<T>,
    private val valueName: String,
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
                val value = item!!::class.java.getDeclaredField(valueName)
                value.isAccessible = true
                nameTv.text = value.get(item) as String
                value.isAccessible = false
                root.setSelect(lastCheckedPos == position, position)
                root.setOnClickListener {
                    val copyLastPos = lastCheckedPos
                    lastCheckedPos = position
                    notifyItemChanged(position)
                    notifyItemChanged(copyLastPos)
                }
            }
        }

        private fun View.setSelect(isSelected: Boolean, position: Int) {
            if (isSelected) {
                selectedItem = itemList[position]
                setBackgroundColor(ContextCompat.getColor(context, R.color.gray_selected))
            } else {
                setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
        }
    }
}
