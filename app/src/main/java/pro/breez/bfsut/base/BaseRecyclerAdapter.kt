package pro.breez.bfsut.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseRecyclerAdapter<VB : ViewBinding, M>(var items: ArrayList<M>) :
    RecyclerView.Adapter<BaseViewHolder<VB>>() {

    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private lateinit var binding: VB

    abstract fun bind(item: M, binding : VB, position: Int)

    fun update(items: ArrayList<M>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        binding =
            inflateMethod.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
        return BaseViewHolder(binding)
    }

    fun getListPosition(position: Int): M {
        return items[position]
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bind(items[position], holder.binding, position)
    }

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
}

class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
}