package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutAcceptBtnBinding
import pro.breez.bfsut.util.setOnClickOnceListener

class AcceptButton(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding = LayoutAcceptBtnBinding.inflate(LayoutInflater.from(context), this, true)
    var isActive: Boolean = true
        set(value) {
            if (value)
                binding.acceptBtn.setBackgroundResource(R.drawable.bg_enabled_btn)
            else
                binding.acceptBtn.setBackgroundResource(R.drawable.bg_disabled_btn)
            field = value
        }

    init {
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.AcceptButton)
            attr.getString(R.styleable.AcceptButton_setText)?.let {
                binding.acceptBtn.text = it
            }
            attr.getBoolean(R.styleable.AcceptButton_isActive, false).let {
                isActive = it
            }
            attr.recycle()
        }
    }

    fun setOnClickListener(block: (isActive: Boolean) -> Unit) {
        binding.acceptBtn.setOnClickOnceListener {
            block.invoke(isActive)
        }
    }
}