package pro.breez.bfsut.ui.main.credit_search_result

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.domain.interactor.CreditsUseCase
import pro.breez.domain.model.output.CreditStatusModel
import pro.breez.domain.model.output.FarmersModel
import javax.inject.Inject

@HiltViewModel
class CreditSearchResultViewModel @Inject constructor(
    private val creditsUseCase: CreditsUseCase
) : BaseViewModel() {

    val farmer: FarmersModel by lazy {
        CreditSearchResultFragmentArgs.fromBundle(requiredArguments()).farmer
    }

    val creditsLV = MutableLiveData<List<CreditStatusModel>>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getCredits()
    }

    private fun getCredits() {
        creditsUseCase.execute(viewModelScope, farmer.id to null) {
            handleResult(it) {
                creditsLV.postValue(it)
            }
        }
    }

    fun creditItemClicked(creditId: String) {
        val args =
            CreditSearchResultFragmentDirections.creditSearchResultToCreditDetail(creditId).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.credit_search_result_to_credit_detail, args))
    }
}