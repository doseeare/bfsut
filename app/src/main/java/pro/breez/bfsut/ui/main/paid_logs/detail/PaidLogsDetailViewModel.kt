package pro.breez.bfsut.ui.main.paid_logs.detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.domain.interactor.PaidLogDetailUseCase
import pro.breez.domain.model.output.PaidLogModel
import pro.breez.domain.model.output.PaidLogsDetailModel
import javax.inject.Inject

@HiltViewModel
class PaidLogsDetailViewModel @Inject constructor(
    private val paidLogsDetailUC: PaidLogDetailUseCase
) : BaseViewModel() {

    val log: PaidLogModel by lazy {
        PaidLogsDetailFragmentArgs.fromBundle(requiredArguments()).log
    }

    val paidLogsDetailLV = MutableLiveData<ArrayList<PaidLogsDetailModel>>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getPaidLogs()
    }

    private fun getPaidLogs() {
        showLoadingView()
        paidLogsDetailUC.execute(viewModelScope, log.id) {
            handleResult(it) {
                paidLogsDetailLV.postValue(it as ArrayList<PaidLogsDetailModel>)
            }
        }
    }

}
