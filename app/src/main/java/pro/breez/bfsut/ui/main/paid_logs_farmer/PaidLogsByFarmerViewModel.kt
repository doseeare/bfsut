package pro.breez.bfsut.ui.main.paid_logs_farmer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.FilterResult
import pro.breez.domain.interactor.PaidLogFarmerUseCase
import pro.breez.domain.model.output.PaidLogsByFarmer
import javax.inject.Inject

@HiltViewModel
class PaidLogsByFarmerViewModel @Inject constructor(
    private val paidLogFarmerUseCase: PaidLogFarmerUseCase
) : BaseViewModel() {
    var filterResult: FilterResult? = null

    val paidLogs = MutableLiveData<List<PaidLogsByFarmer>>()

    fun getPaidLogs() {
        paidLogFarmerUseCase.execute(viewModelScope, filterResult?.toBody()) {
            handleResult(it) {
                paidLogs.postValue(it)
            }
        }
    }
}