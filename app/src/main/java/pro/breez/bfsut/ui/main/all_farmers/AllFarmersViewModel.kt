package pro.breez.bfsut.ui.main.all_farmers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.domain.interactor.FarmersUseCase
import pro.breez.domain.model.output.FarmersModel
import javax.inject.Inject

@HiltViewModel
class AllFarmersViewModel @Inject constructor(
    private val farmersUseCase: FarmersUseCase
) : BaseViewModel() {

    val farmers = SingleLiveEvent<ArrayList<FarmersModel>>()

    fun backClicked() {
        popBackStack.startEvent(null)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        farmersUseCase.execute(viewModelScope) {
            handleResult(it) {
                farmers.postEvent(it as ArrayList)
            }
        }
    }

}