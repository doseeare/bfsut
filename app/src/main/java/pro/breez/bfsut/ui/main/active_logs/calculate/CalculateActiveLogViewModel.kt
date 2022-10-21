package pro.breez.bfsut.ui.main.active_logs.calculate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.domain.interactor.CalculateActiveLogUseCase
import pro.breez.domain.interactor.SaveMilkChangesUseCase
import pro.breez.domain.model.input.MilkChangesBody
import pro.breez.domain.model.output.LogsModel
import javax.inject.Inject

@HiltViewModel
class CalculateActiveLogViewModel @Inject constructor(
    private val calculateUc: CalculateActiveLogUseCase,
    private val saveMilkChangesUc: SaveMilkChangesUseCase
) : BaseViewModel() {
    val currentLogLV = MutableLiveData<LogsModel>()

    private val activeLog: LogsModel by lazy {
        CalculateActiveLogsFragmentArgs.fromBundle(requiredArguments()).activeLogs
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        currentLogLV.postValue(activeLog)
    }

    fun saveChanges(log: LogsModel, morning: String, evening: String) {
        if (morning.isNotEmpty() && evening.isEmpty()) {
            showErrorSnackbar("Заполните все поля")
            return
        }
        showLoadingView()
        val morningChanges = morning.replace("л", "").replace(" ", "").toInt()
        val eveningChanges = evening.replace("л", "").replace(" ", "").toInt()
        val body = MilkChangesBody(
            morning = morningChanges,
            evening = eveningChanges
        )
        saveMilkChangesUc.execute(viewModelScope, log.id to body) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl()
                dialog.setIcon(R.drawable.ic_alert)
                dialog.setTitle("Изменения были сохранены")
                dialog.setSubTitle("Отличная работа")
                dialog.setDismissListener {
                    popBackStack.startEvent(Unit)
                }
                showAlertDialog.startEvent(dialog)
            }
        }

    }

    fun calculateLog(id: String) {
        showLoadingView()
        calculateUc.execute(viewModelScope, id) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl()
                dialog.setIcon(R.drawable.ic_alert)
                dialog.setTitle("Рассчет был произведен")
                dialog.setSubTitle("Отличная работа")
                dialog.setDismissListener {
                    popBackStack.startEvent(Unit)
                }
                showAlertDialog.startEvent(dialog)
            }
        }
    }
}