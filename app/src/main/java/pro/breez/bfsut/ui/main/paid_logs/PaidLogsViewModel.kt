package pro.breez.bfsut.ui.main.paid_logs

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.log.LogFragmentDirections
import pro.breez.domain.interactor.PaidLogsUseCase
import pro.breez.domain.model.output.PaidLogModel
import javax.inject.Inject

@HiltViewModel
class PaidLogsViewModel @Inject constructor(
    private val paidLogsUC: PaidLogsUseCase
) : BaseViewModel() {

    val paidLogsLV = MutableLiveData<ArrayList<PaidLogModel>>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getPaidLogs()
    }

    fun getPaidLogs() {
        paidLogsUC.execute(viewModelScope) {
            handleResult(it) {
                paidLogsLV.postValue(it as ArrayList)
            }
        }
    }

    fun itemClicked(item: PaidLogModel) {
        val args = LogFragmentDirections.logFragmentToPaidDetailLog(item).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.navigation_log_detail, args))
    }
}