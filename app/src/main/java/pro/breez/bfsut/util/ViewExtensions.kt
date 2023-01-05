package pro.breez.bfsut.util

import android.os.SystemClock
import android.view.MotionEvent
import android.view.View

fun View.visibility(b: Boolean?) {
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

fun View.setOnDisableClickListener(block: () -> Unit) {
    this.setOnTouchListener { v, event ->
        if (!isEnabled && event?.action == MotionEvent.ACTION_DOWN) {
            block.invoke()
        }
        performClick()
        return@setOnTouchListener false
    }
}


