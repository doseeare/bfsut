package pro.breez.bfsut.custom.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutAddCreditEdittextBinding

class AddCreditEditText(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutAddCreditEdittextBinding.inflate(LayoutInflater.from(context), this, true)

    var onClicked: () -> Unit = { }

    var text: String
        set(value) {
            binding.edittext.setText(value)
        }
        get() {
            return binding.edittext.text.toString()
        }

    var onTextChanged: (String) -> Unit = { }

    init {
        binding.edittext.doOnTextChanged { text, start, before, count ->
            onTextChanged.invoke(text.toString())
        }
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.AddCreditEditText)

            attr.getString(R.styleable.AddCreditEditText_hint)?.let {
                binding.edittext.hint = it
            }

            attr.getString(R.styleable.AddCreditEditText_title)?.let {
                binding.titleTv.setText(it)
            }

            attr.getString(R.styleable.AddCreditEditText_helper)?.let {
                binding.helper.visibility = View.VISIBLE
                binding.helper.text = it
            }

            attr.getInt(R.styleable.AddCreditEditText_type, 0).let {
                when (it) {
                    0 -> {
                        binding.rootButton.visibility = View.GONE
                    }
                    1 -> {
                        binding.edittext.isEnabled = false
                        binding.dropImg.visibility = View.VISIBLE
                        binding.rootButton.visibility = View.VISIBLE
                        binding.rootButton.setBackgroundColor(Color.TRANSPARENT)
                        binding.rootButton.setOnClickListener {
                            onClicked.invoke()
                        }

                    }
                }
            }

        }
    }


}