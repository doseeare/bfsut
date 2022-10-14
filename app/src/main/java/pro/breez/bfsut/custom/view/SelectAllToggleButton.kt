package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.SelectAllToggleLayoutBinding
import pro.breez.bfsut.util.setOnClickOnceListener

class SelectAllToggleButton(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        SelectAllToggleLayoutBinding.inflate(LayoutInflater.from(context), this, true)

   private var selectAll = false

    fun setToggleCheckedListener(block: (checked: Boolean) -> Unit) {
        binding.invisibleBtn.setOnClickOnceListener {
            setChanges(selectAll)
            block.invoke(selectAll)
        }
    }

    fun setChanges(selectAll : Boolean){
        if (!selectAll) {
            binding.container.setBackgroundResource(R.drawable.bg_choose_all_off)
            binding.leftImg.setImageResource(R.drawable.ic_close_custom)
            binding.titleTv.text = "Сбросить все"
        } else {
            binding.container.setBackgroundResource(R.drawable.bg_choose_all_on)
            binding.leftImg.setImageResource(R.drawable.ic_checkbox_custom)
            binding.titleTv.text = "Выбрать все"
        }
        this.selectAll = !selectAll
    }
}