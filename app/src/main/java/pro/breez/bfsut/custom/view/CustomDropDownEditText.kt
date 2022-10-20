package pro.breez.bfsut.custom.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutDropDownEditTextBinding
import pro.breez.bfsut.util.setOnClickOnceListener

class CustomDropDownEditText(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutDropDownEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    var onClicked: () -> Unit = { }

    var text: String
        set(value) {
            binding.edittext.setText(value)
        }
        get() {
            return binding.edittext.text.toString()
        }

    var error: Boolean = false
        set(value) {
            if (value) {
                binding.border.setBackgroundResource(R.drawable.bg_rounded_error)
            } else {
                binding.border.setBackgroundResource(R.drawable.bg_rounded_shape_darker)
            }
            field = value
        }

    val editText: EditText
        get() {
            return binding.edittext
        }

    val imageView: ImageView
        get() {
            return binding.dropImg
        }

    val isFieldEmpty: Boolean
        get() = binding.edittext.text.isNullOrEmpty()

    var onTextChanged: (String) -> Unit = { }

    fun setOnClickListener(block: () -> Unit) {
        binding.rootButton.setOnClickOnceListener {
            block.invoke()
        }
    }

    fun ifEmptyError(): Boolean {
        return if (text.isEmpty()) {
            error = true
            /*isEmpty = */ true
        } else {
            error = false
            /*isEmpty = */  false
        }
    }

    init {
        binding.edittext.doOnTextChanged { text, start, before, count ->
            onTextChanged.invoke(text.toString())
        }
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.CustomDropDownEditText)

            attr.getString(R.styleable.CustomDropDownEditText_hint)?.let {
                binding.edittext.hint = it
            }

            attr.getString(R.styleable.CustomDropDownEditText_title)?.let {
                binding.titleTv.setText(it)
            }

            attr.getString(R.styleable.CustomDropDownEditText_helper)?.let {
                binding.helper.visibility = View.VISIBLE
                binding.helper.text = it
            }

            attr.getResourceId(R.styleable.CustomDropDownEditText_icon_end, R.drawable.ic_drop)
                .let {
                    binding.dropImg.setImageResource(it)
                }

            attr.getInt(R.styleable.CustomDropDownEditText_type, 0).let {
                when (it) {
                    0 -> {
                        binding.rootButton.visibility = View.INVISIBLE
                        binding.rootButton.setOnClickOnceListener {
                            binding.edittext.requestFocus()
                        }
                    }
                    1 -> {
                        binding.edittext.isEnabled = false
                        binding.dropImg.visibility = View.VISIBLE
                        binding.rootButton.visibility = View.VISIBLE
                        binding.rootButton.setBackgroundColor(Color.TRANSPARENT)
                        binding.rootButton.setOnClickOnceListener {
                            onClicked.invoke()
                        }
                    }
                    2 -> {
                        binding.edittext.isEnabled = false
                        binding.dropImg.visibility = View.GONE
                        binding.rootButton.visibility = View.VISIBLE
                        binding.rootButton.setBackgroundColor(Color.TRANSPARENT)
                        binding.rootButton.setOnClickOnceListener {
                            onClicked.invoke()
                        }
                    }
                }
            }
        }
    }
}