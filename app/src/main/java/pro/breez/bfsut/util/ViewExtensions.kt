package pro.breez.bfsut.util

import android.os.SystemClock
import android.view.View
import androidx.fragment.app.Fragment

fun View.setGoneIfFalse(b: Boolean?) {
    if (b != null)
        if (b) this.visibility = View.VISIBLE else this.visibility = View.GONE
}

fun View.setOnClickOnceListener(clickInterval: Long = 500L, action: (View) -> Unit) {
    var mLastClickTime = 0L
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - mLastClickTime < clickInterval) {
            return@setOnClickListener
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        action.invoke(it)
    }
}

