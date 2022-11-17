package pro.breez.bfsut.ui.main.credit_status

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.CreditStatusEnum
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.credit.CreditsFragmentDirections
import pro.breez.domain.interactor.CreditsUseCase
import pro.breez.domain.model.output.CreditStatusModel
import javax.inject.Inject

@HiltViewModel
class CreditStatusViewModel @Inject constructor(
    private val creditsUseCase: CreditsUseCase
) : BaseViewModel() {

    var creditStatus: CreditStatusEnum? = null

    val creditsLV = MutableLiveData<List<CreditStatusModel>>()

    fun getCredits() {
        creditsUseCase.execute(viewModelScope, null to creditStatus?.key) {
            handleResult(it) {
                creditsLV.postValue(it)
            }
        }
    }

    fun creditItemClicked(creditId: String) {
        val args = CreditsFragmentDirections.creditToCreditsDetail(creditId).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.credit_to_credits_detail, args))
    }
}