package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class ParentViewPager(context: Context, attributeSet: AttributeSet?) : ViewPager(context, attributeSet) {

    override fun canScroll(v: View?, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        if (v != this && v is ViewPager) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y)

    }
}