package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutToolbarBinding
import pro.breez.bfsut.util.setOnClickOnceListener

class Toolbar(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    private var onOptionClicked: (() -> Unit)? = null

    fun setOnBackClickListener(block: () -> Unit) {
        binding.toolbarBackBtn.setOnClickOnceListener {
            block.invoke()
        }
    }

    fun setOnOptionClickedListener(block: () -> Unit) {
        onOptionClicked = block
    }

    init {
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.Toolbar)

            attr.getString(R.styleable.Toolbar_toolbarLabel)?.let {
                binding.toolbarTitle.text = it
            }

            attr.getResourceId(R.styleable.Toolbar_option_img, 0).let {
                if (it != 0) {
                    binding.toolbarOptionBtn.visibility = View.VISIBLE
                    binding.toolbarOptionBtn.setImageResource(it)
                    binding.toolbarOptionBtn.setOnClickOnceListener {
                        onOptionClicked?.invoke()
                    }
                }
            }
        }
    }

    fun setTitle(title: String?) {
        binding.toolbarTitle.text = title
    }
}