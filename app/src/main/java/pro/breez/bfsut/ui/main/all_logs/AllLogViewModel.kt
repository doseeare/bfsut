package pro.breez.bfsut.ui.main.all_logs

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
import pro.breez.domain.interactor.base.AllLogsUseCase
import pro.breez.domain.model.output.LogsModel
import javax.inject.Inject

@HiltViewModel
class AllLogViewModel @Inject constructor(
    private val allLogsUC: AllLogsUseCase
) : BaseViewModel() {

    val allLogsLV = MutableLiveData<ArrayList<LogsModel>>()
    var filterResult: FilterResult? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getAllLogs()
    }

    fun getAllLogs() {
        allLogsUC.execute(viewModelScope, filterResult?.toBody()) {
            handleResult(it) {
                allLogsLV.postValue(it as ArrayList<LogsModel>)
            }
        }
    }

    fun itemClick(item: LogsModel) {
        val args = LogFragmentDirections.logFragmentToCalculateActiveLog(item).arguments
        when (item.status) {
            "active" -> {
                if (DateUtil.isToday(item.date))
                    navigateToFragment.startEvent(
                        FragmentTransaction(
                            R.id.calculate_active_logs,
                            args
                        )
                    )
                else showBottomSheetFragment.startEvent(
                    CalculateBottomSheetFragment.newInstance(
                        item
                    )
                )
            }
            "paid" -> {
                val bottomSheet = CalculateBottomSheetFragment.newInstance(item)
                showBottomSheetFragment.startEvent(bottomSheet)
            }
            else -> {
                return
            }
        }
    }
}