package pro.breez.bfsut.util.alert.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import pro.breez.bfsut.databinding.LayoutAlertDialogBinding

class AlertDialogBuilderImpl : AlertDialogBuilder {
    private var title = ""
    private var subTitle = ""

    @DrawableRes
    private var icon = 0

    private val onDismiss = MutableLiveData<Unit>()

    override fun setTitle(title: String): AlertDialogBuilder {
        this.title = title
        return this
    }

    override fun setSubTitle(title: String): AlertDialogBuilder {
        this.subTitle = title
        return this
    }

    override fun setIcon(icon: Int): AlertDialogBuilder {
        this.icon = icon
        return this
    }

    override fun create(context: Context): Dialog {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = LayoutAlertDialogBinding.inflate(inflater)

        val alertDialog = Dialog(context)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setContentView(binding.root)
        binding.title.text = title
        binding.subTitle.text = subTitle
        binding.iconImg.setImageResource(icon)
        alertDialog.setOnDismissListener {
            onDismiss.postValue(Unit)
        }
        alertDialog.show()
        return alertDialog
    }

    override fun setDismissListener(onDismiss: () -> Unit): AlertDialogBuilder {
        this.onDismiss.observeForever { onDismiss.invoke() }
        return this
    }
}