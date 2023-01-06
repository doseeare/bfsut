package pro.breez.bfsut.custom.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutDropDownEditTextBinding
import pro.breez.bfsut.util.ifTrue
import pro.breez.bfsut.util.isNull
import pro.breez.bfsut.util.setOnClickOnceListener

class CustomDropDownEditText(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private var helperText: String? = null
    private var errorText: String? = null

    private val binding =
        LayoutDropDownEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    private var conditionClearError: (() -> Boolean)? = null

    var onClicked: () -> Unit = { }

    var text: String
        set(value) {
            binding.edittext.setText(value)
            binding.titleTv.setTextColor(ContextCompat.getColor(context, R.color.gray_text))
        }
        get() {
            return binding.edittext.text.toString()
        }

    var error: Boolean = false
        set(value) {
            if (value) {
                binding.border.setBackgroundResource(R.drawable.bg_rounded_error)
                binding.helper.setTextColor(ContextCompat.getColor(context, R.color.error_color))
                binding.helper.visibility = View.VISIBLE
            } else {
                binding.border.setBackgroundResource(R.drawable.bg_rounded_shape_darker)
                if (helperText != null) {
                    binding.helper.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.gray_text
                        )
                    )
                    binding.helper.text = helperText
                } else {
                    binding.helper.visibility = View.GONE
                }
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

    val isEmpty: Boolean
        get() = binding.edittext.text.isNullOrBlank()

    var onTextChanged: ((String) -> Unit)? = null

    var isSuccessFilled = false

    fun setOnClickListener(block: () -> Unit) {
        binding.rootButton.setOnClickOnceListener {
            block.invoke()
        }
    }

    fun onTextChanged(block: (String) -> Unit) {
        onTextChanged = block
    }

    fun reset() {
        binding.edittext.setText("")
        binding.titleTv.setTextColor(ContextCompat.getColor(context, R.color.text_bold_color))
    }

    fun ifEmptyError(): Boolean = if (text.isBlank()) {
        error = true
        /*isEmpty = */ true
    } else {
        error = false
        /*isEmpty = */  false
    }

    fun setCustomConditionError(block: () -> Boolean) {
        conditionClearError = block
    }

    fun textOrNull(): String? {
        return if (text.isBlank() || text.isEmpty()) null
        else text
    }

    init {
        binding.edittext.doOnTextChanged { text, _, _, _ ->
            val filledText = text.toString()

            if (conditionClearError.isNull()) {
                if (filledText.isNotEmpty() && filledText.isNotBlank()) {
                    binding.titleTv.setTextColor(ContextCompat.getColor(context, R.color.gray_text))
                    isSuccessFilled = true
                    error = false
                } else {
                    isSuccessFilled = false
                    binding.titleTv.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.text_bold_color
                        )
                    )
                }
            } else {
                val customCondition = conditionClearError?.invoke()
                customCondition?.ifTrue {
                    error = false
                }
                isSuccessFilled = customCondition!!
            }
            onTextChanged?.invoke(filledText)
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
                helperText = it
            }

            attr.getBoolean(R.styleable.CustomDropDownEditText_important, false).let {
                binding.importantImg.isVisible = it
            }

            attr.getResourceId(
                R.styleable.CustomDropDownEditText_icon_end,
                R.drawable.ic_arrow_down
            )
                .let {
                    binding.dropImg.setImageResource(it)
                }

            attr.getInt(R.styleable.CustomDropDownEditText_type, 0).let {
                setType(it)
            }
            attr.recycle()
        }
    }

    fun setType(type: Int) {
        when (type) {
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