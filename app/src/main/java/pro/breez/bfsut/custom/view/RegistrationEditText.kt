package pro.breez.bfsut.custom.view

import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutRegistrationEdittextBinding
import pro.breez.bfsut.util.setGoneIfFalse

class RegistrationEditText(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutRegistrationEdittextBinding.inflate(LayoutInflater.from(context), this, true)

    private var passwordToggleSrc = R.drawable.ic_password_hide

    private var passwordToggleOn = false
        set(value) {
            field = value
            if (value) {
                passwordToggleSrc = R.drawable.ic_password_show
                binding.edittext.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                passwordToggleSrc = R.drawable.ic_password_hide
                binding.edittext.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
        }

    var text: String
        set(value) {
            binding.edittext.setText(value)
        }
        get() {
            return binding.edittext.text.toString()
        }

    val editText: EditText
        get() = binding.edittext

    var showError: Boolean = false
        set(value) {
            field = value
            val errorBorderBg =
                if (value) R.drawable.bg_auth_edittext_error
                else R.drawable.bg_auth_edittext_default
            binding.border.setBackgroundResource(errorBorderBg)
            binding.error.setGoneIfFalse(value)
        }

    init {
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.RegistrationEditText)

            attr.getString(R.styleable.RegistrationEditText_hint)?.let {
                binding.edittext.hint = it
            }

            attr.getString(R.styleable.RegistrationEditText_title)?.let {
                binding.titleTv.text = it
            }

            attr.getString(R.styleable.RegistrationEditText_error)?.let {
                binding.error.text = it
            }

            attr.getBoolean(R.styleable.RegistrationEditText_is_password, false).let {
                if (it) {
                    passwordToggleOn = true
                    binding.passwordToggle.visibility = VISIBLE
                    binding.passwordToggle.setOnClickListener {
                        passwordToggleOn = !passwordToggleOn
                        binding.passwordToggle.setImageResource(passwordToggleSrc)
                    }
                }
            }

        }
    }


}