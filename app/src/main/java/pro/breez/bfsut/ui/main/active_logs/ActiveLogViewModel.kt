package pro.breez.bfsut.ui.main.active_logs

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.FilterResult
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.active_logs.calculate.CalculateBottomSheetFragment
import pro.breez.bfsut.ui.main.log.LogFragmentDirections
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.alert.QuestionDialog
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.domain.interactor.ActiveLogsUseCase
import pro.breez.domain.interactor.CalculateActiveLogsUseCase
import pro.breez.domain.model.output.LogsModel
import javax.inject.Inject

@HiltViewModel
class ActiveLogViewModel @Inject constructor(
    private val activeLogsUseCase: ActiveLogsUseCase,
    private val calculateActiveLogs: CalculateActiveLogsUseCase
) : BaseViewModel() {
    private val selectedLogsLV = MutableLiveData<ArrayList<LogsModel>>()
    val activeLogsLV = MutableLiveData<ArrayList<LogsModel>>()
    val selectedLogsPriceInfoLV = MutableLiveData<Pair<Int, Int>>()

    var filterResult: FilterResult? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getActiveLogs()
        checkLogsPriceInfo()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getActiveLogs()
    }

    fun getActiveLogs() {
        activeLogsUseCase.execute(viewModelScope, filterResult?.toBody()) {
            handleResult(it) {
                activeLogsLV.postValue(it as ArrayList<LogsModel>)
            }
        }
    }

    fun checkBoxChanged(item: LogsModel, isSelected: Boolean) {
        val currentSelectedList = if (selectedLogsLV.value == null) {
            arrayListOf()
        } else {
            ArrayList<LogsModel>(selectedLogsLV.value!!)
        }
        if (isSelected) {
            currentSelectedList.add(item)
        } else {
            currentSelectedList.remove(item)
        }
        selectedLogsLV.postValue(currentSelectedList)
    }

    fun selectAll(isSelected: Boolean) {
        activeLogsLV.value?.let { originalList ->
            if (isSelected) {
                selectedLogsLV.postValue(originalList)
            } else {
                selectedLogsLV.postValue(arrayListOf())
            }
        }
    }

    //todo изменить overall на int, после обновы сервера
    private fun checkLogsPriceInfo() {
        selectedLogsLV.observeForever {
            var totalLiters = 0
            var totalAmount = 0
            it.forEach {
                totalLiters += it.evening + it.morning
                totalAmount += (it.overall.toInt())
            }
            selectedLogsPriceInfoLV.postValue(totalLiters to totalAmount)
        }
    }

    fun itemClicked(item: LogsModel) {
        val args = LogFragmentDirections.logFragmentToCalculateActiveLog(item).arguments
        if (DateUtil.isToday(item.date)) {
            navigateToFragment.startEvent(
                FragmentTransaction(
                    R.id.calculate_active_logs,
                    args
                )
            )
        } else {
            showBottomSheetFragment.startEvent(
                CalculateBottomSheetFragment.newInstance(
                    item
                )
            )
        }
    }

    fun calculateLogs() {
        if (selectedLogsLV.value == null) return
        val dialog = QuestionDialog()
        val title: String = if (selectedLogsLV.value!!.size == 1) {
            val selectedFarmer = selectedLogsLV.value!![0]
            "Рассчитать ${selectedFarmer.farmer_name}\n на ${selectedFarmer.overall} сом?"
        } else {
            val totalAmount = selectedLogsLV.value!!.sumOf { it.overall.toInt() }
            "Рассчитать фермеров на $totalAmount сом?"
        }

        dialog.setTitle(title)
        dialog.onPositiveBtnClicked {
            showLoadingView()
            dialog.dismiss()
            calculateActiveLogs.execute(viewModelScope, selectedLogsLV.value) {
                handleResult(it) {
                    val dialog = AlertDialogBuilderImpl().apply {
                        setIcon(R.drawable.ic_alert)
                        setTitle("Рассчет был произведен")
                        setSubTitle("Отличная работа")
                        setDismissListener {
                            getActiveLogs()
                        }
                    }
                    showAlertDialog.startEvent(dialog)
                }
            }
        }
        showDialogFragment.startEvent(dialog)
    }
}