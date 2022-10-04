package pro.breez.bfsut.util.alert.alternative

/*
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import pro.breez.bfsut.util.alert.alternative.AlternativeDialogBuilder
import pro.breez.mobimarket.R
import pro.breez.mobimarket.databinding.AlternativeAlertDialogViewBinding
import pro.breez.mobimarket.model.AlternativeAlertOptions

class AlternativeDialogBuilderImpl : AlternativeDialogBuilder {

    private var onPositiveClicked: (() -> Unit)? = null
    private var onNegativeClicked: (() -> Unit)? = null
    private var negativeButtonTitle: Int? = null
    private var positiveButtonTitle: Int? = null
    private var icon: Int? = null
    private var message: Int? = null
    private var title: Int? = null
    private var titleAsString: String? = null
    private var isCancelable: Boolean = true
    private val backgroundId: Int = R.drawable.alternative_alert_dialog_background

    override fun setTitle(title: Int): AlternativeDialogBuilder {
        this.title = title
        return this
    }

    override fun setTitle(title: String): AlternativeDialogBuilder {
        this.titleAsString = title
        return this
    }

    override fun setMessage(message: Int): AlternativeDialogBuilder {
        this.message = message
        return this
    }

    override fun setIcon(drawableId: Int): AlternativeDialogBuilder {
        this.icon = drawableId
        return this
    }

    override fun setPositiveTitle(title: Int): AlternativeDialogBuilder {
        this.positiveButtonTitle = title
        return this
    }

    override fun setNegativeTitle(title: Int): AlternativeDialogBuilder {
        this.negativeButtonTitle = title
        return this
    }

    override fun setOnPositiveButtonClicked(onClicked: () -> Unit): AlternativeDialogBuilder {
        this.onPositiveClicked = onClicked
        return this
    }

    override fun setOnNegativeButtonClicked(onClicked: () -> Unit): AlternativeDialogBuilder {
        this.onNegativeClicked = onClicked
        return this
    }

    override fun setCancelable(cancelable: Boolean): AlternativeDialogBuilder {
        this.isCancelable = cancelable
        return this
    }

    override fun fromOptions(
        alternativeAlertOptions: AlternativeAlertOptions
    ): AlternativeDialogBuilder {

        title = alternativeAlertOptions.title
        message = alternativeAlertOptions.message
        positiveButtonTitle = alternativeAlertOptions.positiveButtonTitle
        negativeButtonTitle = alternativeAlertOptions.negativeButtonTitle
        icon = alternativeAlertOptions.imageId
        isCancelable = alternativeAlertOptions.cancelable

        return this
    }

    override fun create(context: Context): AlertDialog {
        val alertDialog: AlertDialog
        val binding =
            AlternativeAlertDialogViewBinding.inflate(LayoutInflater.from(context), null, false)

        if (titleAsString == null) {
            binding.textViewTitle.text = context.getString(title!!)
        } else {
            binding.textViewTitle.text = titleAsString
        }
        binding.textViewMessage.text = message?.let { context.getString(it) }
        binding.buttonPositive.text = context.getString(positiveButtonTitle!!)
        binding.textViewNegativeButton.text = context.getString(negativeButtonTitle!!)
        binding.root.setBackgroundResource(backgroundId)
        icon?.let { binding.imageViewLogo.setImageResource(it) }

        alertDialog = AlertDialog.Builder(context).setView(binding.root).create()
        alertDialog.setCancelable(isCancelable)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.buttonPositive.setOnClickListener {
            onPositiveClicked?.invoke()
            alertDialog.dismiss()
        }

        binding.textViewNegativeButton.setOnClickListener {
            onNegativeClicked?.invoke()
            alertDialog.dismiss()
        }
        return alertDialog
    }
}*/
