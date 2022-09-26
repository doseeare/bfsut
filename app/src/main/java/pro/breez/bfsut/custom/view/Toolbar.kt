package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutToolbarBinding

class Toolbar(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    fun setOnBackClickListener(block: () -> Unit) {
        binding.toolbarBackBtn.setOnClickListener {
            block.invoke()
        }
    }

    init {
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.Toolbar)

            attr.getString(R.styleable.Toolbar_toolbarLabel)?.let {
                binding.toolbarTitle.text = it
            }
        }
    }
}