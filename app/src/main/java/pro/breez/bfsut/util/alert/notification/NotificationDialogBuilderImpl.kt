/*
package pro.breez.mobimarket.utility.alert.notification

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import pro.breez.bfsut.util.alert.notification.NotificationDialogBuilder
import pro.breez.mobimarket.R
import pro.breez.mobimarket.databinding.NoNetworkConnectionAlertBinding
import pro.breez.mobimarket.model.NotificationAlertOptions

class NotificationDialogBuilderImpl : NotificationDialogBuilder {

    private var onPositiveClicked: (() -> Unit)? = null
    private var positiveButtonTitle: Int? = null
    private var icon: Int? = null
    private var message: Int? = null
    private var title: Int? = null
    private var isCancelable: Boolean = false
    private val backgroundId: Int = R.drawable.alternative_alert_dialog_background

    override fun setTitle(title: Int): NotificationDialogBuilder {
        this.title = title
        return this
    }

    override fun setMessage(message: Int): NotificationDialogBuilder {
        this.message = message
        return this
    }

    override fun setIcon(drawableId: Int): NotificationDialogBuilder {
        this.icon = drawableId
        return this
    }

    override fun setPositiveTitle(title: Int): NotificationDialogBuilder {
        this.positiveButtonTitle = title
        return this
    }

    override fun setOnPositiveButtonClicked(onClicked: () -> Unit): NotificationDialogBuilder {
        this.onPositiveClicked = onClicked
        return this
    }

    override fun setCancelable(cancelable: Boolean): NotificationDialogBuilder {
        isCancelable = cancelable
        return this
    }

    override fun fromOptions(notificationAlertOptions: NotificationAlertOptions): NotificationDialogBuilder {
        positiveButtonTitle = notificationAlertOptions.positiveButtonTitle
        icon = notificationAlertOptions.imageId
        message = notificationAlertOptions.message
        title = notificationAlertOptions.title
        isCancelable = notificationAlertOptions.cancelable
        return this
    }

*/
/*    override fun create(context: Context): AlertDialog {
        val alertDialog: AlertDialog
        val binding =
            NoNetworkConnectionAlertBinding.inflate(LayoutInflater.from(context), null, false)
        binding.textViewTitle.text = context.getString(title!!)
        binding.textViewMessage.text = message?.let { context.getString(it) }
        binding.buttonSubmit.text = context.getString(positiveButtonTitle!!)
        binding.root.setBackgroundResource(backgroundId)
        binding.imageView.setImageResource(icon!!)

        alertDialog = AlertDialog.Builder(context).setView(binding.root).create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCancelable(isCancelable)

        binding.buttonSubmit.setOnClickListener {
            onPositiveClicked?.invoke()
            alertDialog.dismiss()
        }
        return alertDialog
    }*//*

}*/
