package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutSelectableButtonBinding
import pro.breez.bfsut.util.setOnClickOnceListener

class SelectableButton(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutSelectableButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private var alternativeButtons: ArrayList<SelectableButton>? = null

    private var onClicked: ((SelectableButton) -> Unit)? = null

    var isActive: Boolean = false
        set(value) {
            if (value) {
                binding.indicatorImg.setImageResource(R.drawable.ic_chooser_red)
                disableAlternatives()
            } else
                binding.indicatorImg.setImageResource(R.drawable.ic_chooser)
            field = value
        }

    var error: Boolean = false
        set(value) {
            if (value) {
                binding.container.setBackgroundResource(R.drawable.bg_rounded_error)
            } else {
                binding.container.setBackgroundResource(R.drawable.bg_shape_selectable_button)
            }
            field = value
        }

    fun setAlternatives(views: ArrayList<SelectableButton>) {
        alternativeButtons = views
    }

    private fun disableAlternatives() {
        error = false
        alternativeButtons?.let {
            for (button in it) {
                button.error = false
                if (button.isActive) {
                    button.isActive = false
                    break
                } else {
                    button.isActive = false
                }
            }
        }
    }

    fun setResultListener(block: (SelectableButton) -> Unit) {
        onClicked = block
    }

    init {
        binding.root.setOnClickOnceListener {
            isActive = !isActive
            onClicked?.invoke(this)
        }

        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.SelectableButton)

            attr.getString(R.styleable.SelectableButton_button_text)?.let {
                binding.textTv.text = it
            }
            attr.getResourceId(R.styleable.SelectableButton_button_image, 0).let {
                binding.iconImg.setImageResource(it)
            }

        }
    }

    companion object {
        fun init(vararg btns: SelectableButton, activated: (SelectableButton) -> Unit) {
            for (i in btns.indices) {
                val filteredBtn = btns.toCollection(ArrayList()).apply {
                    removeAt(i)
                }
                btns[i].setAlternatives(filteredBtn)
                btns[i].setResultListener {
                    activated.invoke(it)
                }
            }
        }
    }

}

