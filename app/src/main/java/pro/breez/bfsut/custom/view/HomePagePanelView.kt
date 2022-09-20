package pro.breez.bfsut.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.LayoutHomePagePanelBinding


class HomePagePanelView(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context) : this(context, null)

    private val binding =
        LayoutHomePagePanelBinding.inflate(LayoutInflater.from(context), this, true)

    @DrawableRes
    private var panelBackground: Int? = null
    private var panelTag = ""
    private var panelBottomTextColor: Int? = null

    init {
        attributeSet?.let {
            val attr =
                context.obtainStyledAttributes(attributeSet, R.styleable.HomePagePanelView)

            attr.getBoolean(R.styleable.HomePagePanelView_is_day, false).let {
                if (it) {
                    panelBackground = R.drawable.bg_home_panel_day
                    panelTag = "Утром"
                    panelBottomTextColor = ContextCompat.getColor(context, R.color.text_bold_color)
                } else {
                    panelBackground =
                        R.drawable.bg_home_panel_night
                    panelTag = "Вечером"
                    panelBottomTextColor = ContextCompat.getColor(context, R.color.white)
                }
            }
        }

        binding.dayContainer.setBackgroundResource(panelBackground!!)
        binding.tagTv.text = panelTag
        binding.priceTv.setTextColor(panelBottomTextColor!!)
        binding.currencyTv.setTextColor(panelBottomTextColor!!)
        binding.literTv.setTextColor(panelBottomTextColor!!)
    }

}