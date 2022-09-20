package pro.breez.bfsut.custom.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutButtonDrawableBinding

class ButtonDrawableView(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutButtonDrawableBinding.inflate(LayoutInflater.from(context), this, true)

    /*    <attr name="text" format="string" />
        <attr name="drawable_end" format="reference" />
        <attr name="drawable_start" format="reference" />
        <attr name="button_background" format="reference" />
        <attr name="text_color" format="reference" />
        <attr name="text_size" format="dimension" />
        <attr name="text_style" format="dimension">
        <flag name="normal" value="0" />
        <flag name="bold" value="1" />
        <flag name="italic" value="2" />*/

    var onClicked: (() -> Unit)? = null

    init {
        binding.rootLayout.setOnClickListener {
            onClicked?.invoke()
        }

        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.ButtonDrawableView)

            attr.getString(R.styleable.ButtonDrawableView_text)?.let {
                binding.titleTv.text = it
            }
            attr.getResourceId(R.styleable.ButtonDrawableView_drawable_end, 0).let {
                binding.drawableEndImg.setImageResource(it)
            }
            attr.getResourceId(R.styleable.ButtonDrawableView_drawable_start, 0).let {
                binding.drawableStartImg.setImageResource(it)
            }
            attr.getResourceId(R.styleable.ButtonDrawableView_text_color, 0).let {
                binding.titleTv.setTextColor(it)
            }

            attr.getInt(R.styleable.ButtonDrawableView_text_style, 0).let {
                when (it) {
                    0 -> binding.titleTv.setTypeface(null, Typeface.NORMAL)
                    1 -> binding.titleTv.typeface = Typeface.DEFAULT_BOLD
                    2 -> binding.titleTv.typeface = Typeface.SERIF
                }
            }
            attr.getResourceId(R.styleable.ButtonDrawableView_button_background, 0).let {
                binding.rootLayout.setBackgroundResource(it)
            }

            attr.getFloat(R.styleable.ButtonDrawableView_text_size, 0f).let {
                binding.titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
            }
        }
    }


}