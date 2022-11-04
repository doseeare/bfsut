package pro.breez.bfsut.ui.main.edit_milk_log

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.util.DateUtil
import pro.breez.domain.interactor.ActiveLogsUseCase
import pro.breez.domain.model.input.FilterBody
import pro.breez.domain.model.output.FarmerCheckModel
import pro.breez.domain.model.output.LogsModel
import javax.inject.Inject

@HiltViewModel
class EditMilkViewModel @Inject constructor(
    private val activeLogsUseCase: ActiveLogsUseCase
) : BaseViewModel() {

    val farmer: FarmerCheckModel by lazy {
        EditMilkFragmentArgs.fromBundle(requiredArguments()).farmer
    }

    val foundLogLV = MutableLiveData<LogsModel>()
    val farmerCheckLV = MutableLiveData<FarmerCheckModel>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        val filterBody = FilterBody(farmer.id, null, DateUtil.getToday(), DateUtil.getToday())
        farmerCheckLV.postValue(farmer)
        activeLogsUseCase.execute(viewModelScope, filterBody) {
            handleResult(it) {
                foundLogLV.postValue(it.first())
            }
        }
    }

    fun editBtnClicked() {
        val args =
            EditMilkFragmentDirections.editMilkToCalculateActiveLog(foundLogLV.value!!).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.calculate_active_logs, args))
    }

}