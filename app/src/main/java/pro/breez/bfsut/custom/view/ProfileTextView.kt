package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutProfileTextviewBinding

class ProfileTextView(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutProfileTextviewBinding.inflate(LayoutInflater.from(context), this, true)

    var text: String? = ""
        set(value) {
            if (value != null) {
                if (value.isNotBlank()) {
                    binding.subTitle.text = value
                } else {
                    binding.subTitle.text = "Не указан"
                }
            } else {
                binding.subTitle.text = "Не указан"
            }
            field = value
        }

    init {
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.ProfileTextView)
            attr.getString(R.styleable.ProfileTextView_top_title).let {
                binding.title.text = it
            }
            attr.getString(R.styleable.ProfileTextView_sub_title).let {
                binding.subTitle.text = it
            }
        }
    }

}