package pro.breez.bfsut.ui.main.active_logs.calculate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.domain.interactor.CalculateActiveLogUseCase
import pro.breez.domain.model.output.LogsModelOut
import javax.inject.Inject

@HiltViewModel
class CalculateActiveLogViewModel @Inject constructor(
    private val calculateUc: CalculateActiveLogUseCase
) : BaseViewModel() {
    val currentLogLV = MutableLiveData<LogsModelOut>()

    private val activeLog: LogsModelOut by lazy {
        CalculateActiveLogsFragmentArgs.fromBundle(requiredArguments()).activeLogs
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        currentLogLV.postValue(activeLog)
    }

    fun calculateLog(id: String) {
        showLoadingView()
        calculateUc.execute(viewModelScope, id) {
            showSelectorDialog
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