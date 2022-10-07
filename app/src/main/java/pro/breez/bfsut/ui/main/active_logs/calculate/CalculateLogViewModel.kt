package pro.breez.bfsut.ui.main.active_logs.calculate

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.domain.interactor.CalculateActiveLogUseCase
import pro.breez.domain.model.output.LogsModel
import javax.inject.Inject

@HiltViewModel
class CalculateLogViewModel @Inject constructor(
    private val calculateUc: CalculateActiveLogUseCase
) : BaseViewModel() {
    var log : LogsModel? = null

    fun calculateLog(id: String) {
        showLoadingView()
        calculateUc.execute(viewModelScope, id) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl()
                dialog.setIcon(R.drawable.ic_alert)
                dialog.setTitle("Рассчет был произведен")
                dialog.setSubTitle("Отличная работа")
                dialog.setDismissListener {
                    popBackStack.startEvent(R.id.navigation_log)
                }
                showAlertDialog.startEvent(dialog)
            }
        }
    }
}