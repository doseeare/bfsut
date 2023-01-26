package pro.breez.bfsut.util.alert.snackbar

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.CustomSnackbarBinding
import pro.breez.bfsut.model.SnackBarMessageOptions
import pro.breez.bfsut.util.setOnClickOnceListener

class SnackbarNotificationBuilder :
    SnackbarNotificationBuilderInterface {

    private var message: String? = null

    @ColorRes
    private var messageTextColor: Int = R.color.white

    @DrawableRes
    private var leftIcon: Int? = null

    @ColorRes
    private var textColor: Int? = null

    @DrawableRes
    private var closeIcon: Int? = null

    @DrawableRes
    private var background: Int? = null
    private var hideLeftIconSpace = false
    private var length: Int = Snackbar.LENGTH_LONG


    override fun setBackground(resourceId: Int): SnackbarNotificationBuilderInterface {
        background = resourceId
        return this
    }

    override fun setMessage(value: String): SnackbarNotificationBuilderInterface {
        message = value
        return this
    }

    override fun setMessageColor(colorId: Int): SnackbarNotificationBuilderInterface {
        messageTextColor = colorId
        return this
    }

    override fun setLeftIcon(resourceId: Int): SnackbarNotificationBuilderInterface {
        leftIcon = resourceId
        return this
    }

    override fun setCloseIcon(resourceId: Int): SnackbarNotificationBuilderInterface {
        closeIcon = resourceId
        return this
    }

    override fun setShowLength(length: Int): SnackbarNotificationBuilderInterface {
        this.length = length
        return this
    }

    override fun hideLeftIconPadding(): SnackbarNotificationBuilderInterface {
        hideLeftIconSpace = true
        return this
    }

    override fun fromOptions(options: SnackBarMessageOptions): SnackbarNotificationBuilderInterface {
        background = options.backgroundId
        message = options.message
        messageTextColor = options.messageTextColor

        if (options.hideLeftSpace) {
            hideLeftIconPadding()
        }

        if (options.imageId != null) {
            leftIcon = options.imageId
        }

        closeIcon = options.closeIcon

        return this
    }

    override fun create(container: View): Snackbar {
        val snackbar = Snackbar.make(container, "", Snackbar.LENGTH_LONG)
        val layout = snackbar.view as Snackbar.SnackbarLayout
        val binding =
            CustomSnackbarBinding.inflate(LayoutInflater.from(snackbar.context), layout, true)

        removeInitialView(layout)
        setPaddings(layout)

        background?.let { binding.rootLayout.setBackgroundResource(it) }
        textColor?.let { binding.textViewTitle.setTextColor(it) }
        binding.textViewTitle.text = message
        binding.textViewTitle.setTextColor(
            ContextCompat.getColor(
                snackbar.context,
                messageTextColor
            )
        )

        val params = binding.rootLayout.layoutParams
        params.width = FrameLayout.LayoutParams.MATCH_PARENT
        binding.rootLayout.layoutParams = params

        leftIcon?.let {
            binding.imageViewLogo.apply {
                visibility = View.VISIBLE
                setImageResource(it)
            }
        }

        closeIcon?.let {
            binding.imageViewClose.apply {
                visibility = View.VISIBLE
                setImageResource(it)
                setOnClickOnceListener {
                    snackbar.dismiss()
                }
            }
        }

        snackbar.duration = length

        if (hideLeftIconSpace) {
            binding.space.visibility = View.GONE
        }

        return snackbar
    }

    private fun removeInitialView(layout: Snackbar.SnackbarLayout) {
        (layout.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView).visibility =
            View.INVISIBLE
        layout.setBackgroundResource(android.R.color.transparent)
        layout.setPadding(0, 0, 0, 0)
    }

    @SuppressLint("RestrictedApi")
    private fun setPaddings(layout: Snackbar.SnackbarLayout) {
        val params = (layout.layoutParams as? FrameLayout.LayoutParams)
        params?.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params?.width = FrameLayout.LayoutParams.MATCH_PARENT
        params?.setMargins(
            params.leftMargin,
            params.topMargin + 32,
            params.rightMargin,
            params.bottomMargin
        )
        params?.let { layout.layoutParams = it }
    }
}
