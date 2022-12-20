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
        if (editText.selectionStart == editText.length() && !editText.text.isNullOrBlank()) {
            moveBackCursor()
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        editText.removeTextChangedListener(this)
        s?.filters = emptyFilter
        val text = s.toString()
        val suffixPosition = text.indexOf(suffix, 0)
        if (!text.contains(suffix)) {
            s?.clear()
            s?.append("$text$suffix")
            moveBackCursor()
        } else if (suffixPosition != text.length - 1 && suffixPosition != -1) {
            val addedChar = s?.get(suffixPosition + 1).toString()
            s?.delete(suffixPosition, text.length)
            s?.append("$addedChar$suffix")
        } else if (editText.text.toString() == suffix) {
            s?.clear()
        }
        if (s?.length != 0)
            if (s?.get(0) == '0' && text.length - 1 > 1) {
                s.delete(0, 1)
            }
        s?.filters = filter
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
