package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutAddMenuBinding

class AddMenuView(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutAddMenuBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.AddMenuView)

            attr.getString(R.styleable.AddMenuView_label)?.let {
                binding.label.text = it
            }
            attr.getResourceId(R.styleable.AddMenuView_icon, 0).let {
                binding.icon.setImageResource(it)
            }

        }
    }


}