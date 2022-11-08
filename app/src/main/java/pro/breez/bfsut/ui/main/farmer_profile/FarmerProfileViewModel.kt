package pro.breez.bfsut.ui.main.farmer_profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.domain.interactor.FarmerProfileUseCase
import pro.breez.domain.model.output.FarmerProfileModel
import javax.inject.Inject

@HiltViewModel
class FarmerProfileViewModel @Inject constructor(
    private val farmerProfileUseCase: FarmerProfileUseCase
) : BaseViewModel() {
    val farmerProfileLV = MutableLiveData<FarmerProfileModel>()

    private val farmerId: String by lazy {
        FarmerProfileFragmentArgs.fromBundle(requiredArguments()).farmerId
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getFarmerProfile()
    }

    private fun getFarmerProfile() {
        showLoadingView()
        farmerProfileUseCase.execute(viewModelScope, farmerId) {
            handleResult(it) {
                farmerProfileLV.postValue(it)
            }
        }
    }

    fun editClicked() {
        farmerProfileLV.value?.let {
            val args =
                FarmerProfileFragmentDirections.profileToEdit(farmerProfileLV.value!!).arguments
            navigateToFragment.startEvent(FragmentTransaction(R.id.profile_to_edit, args))
        }
    }
}
