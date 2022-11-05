package pro.breez.bfsut.ui.main.all_farmers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.model.navigation.FragmentTransaction
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

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        farmersUseCase.execute(viewModelScope) {
            handleResult(it) {
                farmers.postEvent(it as ArrayList)
            }
        }
    }

    fun farmerClicked(item: FarmersModel) {
        val args = AllFarmersFragmentDirections.homeFragmentToProfileFarmer(item.id).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.navigation_farmer_profile, args))
    }

}