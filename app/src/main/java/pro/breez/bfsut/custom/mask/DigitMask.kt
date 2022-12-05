package pro.breez.bfsut.custom.mask

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText

class DigitMask(val editText: EditText, private val suffix: String) : TextWatcher {
    private val filter: Array<InputFilter> = editText.filters
    private val emptyFilter = emptyArray<InputFilter>()

    init {
        editText.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (editText.text.isNotBlank() &&
                keyCode == KeyEvent.KEYCODE_DEL &&
                editText.selectionStart == editText.length()
            ) {
                moveBackCursor()
            }
            return@OnKeyListener false
        })
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        editText.filters = emptyFilter
        if (editText.selectionStart == editText.length() && !editText.text.isNullOrBlank()) {
            moveBackCursor()
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        editText.filters = filter
    }

    override fun afterTextChanged(s: Editable?) {
        editText.removeTextChangedListener(this)
        val text = s.toString()
        val suffixPosition = text.indexOf(suffix, 0)
        if (!text.contains(suffix)) {
            editText.setText("$text$suffix")
            moveBackCursor()
        }
        else if (suffixPosition != text.length - 1 && suffixPosition != -1) {
            s?.delete(suffixPosition + 1, text.length)
        } else if (editText.text.toString() == suffix) {
            s?.clear()
        }
        editText.addTextChangedListener(this)
    }

    private fun moveBackCursor() {
        editText.apply {
            setSelection(length() - 1)
        }
    }

}

fun EditText.addDigitMask(suffix: String) {
    addTextChangedListener(DigitMask(this, suffix))
}
