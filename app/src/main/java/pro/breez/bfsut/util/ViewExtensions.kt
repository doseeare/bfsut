package pro.breez.bfsut.util

import android.view.View

fun View.setGoneIfFalse(b: Boolean?) {
    if (b != null)
    if (b) this.visibility = View.VISIBLE else this.visibility = View.GONE
}
