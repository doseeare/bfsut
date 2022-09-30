package pro.breez.bfsut.ui.main.active_logs

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.domain.interactor.ActiveLogsUseCase
import pro.breez.domain.model.output.LogsModelOut
import javax.inject.Inject

@HiltViewModel
class ActiveLogViewModel @Inject constructor(
    private val activeLogsUseCase: ActiveLogsUseCase
) : BaseViewModel() {

    private val selectedLogsLV = MutableLiveData<ArrayList<LogsModelOut>>()

    val activeLogsLV = MutableLiveData<ArrayList<LogsModelOut>>()
    val selectedLogsPriceInfoLV = MutableLiveData<Pair<Int, Int>>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getActiveLogs()
        checkLogsPriceInfo()
    }

    private fun getActiveLogs() {
        activeLogsUseCase.execute(viewModelScope) {
            handleResult(it) {
                activeLogsLV.postValue(it as ArrayList<LogsModelOut>)
            }
        }
    }

    fun checkBoxChanged(item: LogsModelOut, isSelected: Boolean) {
        val currentSelectedList = if (selectedLogsLV.value == null) {
            arrayListOf()
        } else {
            ArrayList<LogsModelOut>(selectedLogsLV.value!!)
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

    fun itemClicked() {
        navigateToFragment.startEvent(FragmentTransaction(R.id.log_fragment_to_calculate_active_log))
    }
}