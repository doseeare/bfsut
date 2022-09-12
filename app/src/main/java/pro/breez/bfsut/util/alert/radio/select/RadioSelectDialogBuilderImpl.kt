package pro.breez.bfsut.util.alert.radio.select

import android.content.Context
import androidx.appcompat.app.AlertDialog
import pro.breez.mobimarket.utility.alert.DialogBuilder

class RadioSelectDialogBuilderImpl : RadioSelectDialogBuilder {
    private var onSelect: ((Int) -> Unit)? = null
    private var message: Int? = null
    private var title: Int? = null
    private var isCancelable: Boolean = false
    private lateinit var collection: Array<String>

    override fun setOnSelect(onSelect: (Int) -> Unit): RadioSelectDialogBuilder {
        this.onSelect = onSelect
        return this
    }

    override fun setItems(collection: Array<String>): RadioSelectDialogBuilder {
        this.collection = collection
        return this
    }

    override fun setTitle(title: Int): DialogBuilder {
        this.title = title
        return this
    }

    override fun setMessage(message: Int): DialogBuilder {
        this.message = message
        return this
    }

    override fun setCancelable(cancelable: Boolean): DialogBuilder {
        isCancelable = cancelable
        return this
    }

    override fun create(context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)

        title?.let { builder.setTitle(it) }
        message?.let { builder.setMessage(it) }

        builder.setItems(collection) { _, which ->
            onSelect?.invoke(which)
        }

        return builder.create()
    }
}