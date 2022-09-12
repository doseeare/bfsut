package pro.breez.bfsut.util.alert.radio.select

import pro.breez.mobimarket.utility.alert.DialogBuilder


interface RadioSelectDialogBuilder:DialogBuilder {
    fun setOnSelect(onSelect: ((Int) -> Unit)): RadioSelectDialogBuilder
    fun setItems(collection: Array<String>): RadioSelectDialogBuilder
}