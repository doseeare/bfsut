package pro.breez.bfsut.ui.main.credit_issued

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.CreditIssueEnum
import pro.breez.bfsut.model.FilterResult
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.credit.CreditsFragmentDirections
import pro.breez.bfsut.ui.main.filter_result.issued.IssuedFilterResultFragmentDirections
import pro.breez.domain.interactor.CreditIssuedUseCase
import pro.breez.domain.model.input.CreditIssuedBody
import pro.breez.domain.model.output.CreditIssuedModel
import pro.breez.domain.model.output.IssuedData
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class CreditIssuedViewModel @Inject constructor(
    private val creditIssuedUseCase: CreditIssuedUseCase
) : BaseViewModel() {

    var creditIssue: CreditIssueEnum? = null
    var filterResult: FilterResult? = null
    var selectedCreditId: String? = null

    private var isFilterMode = false
    val creditIssuedLV = MutableLiveData<CreditIssuedModel>()

    fun initArgs(serializable: Serializable?) {
        if (serializable != null)
            when (serializable) {
                is CreditIssueEnum -> {
                    creditIssue = serializable
                    isFilterMode = false
                }
                is FilterResult -> {
                    filterResult = serializable
                    isFilterMode = true
                }
            }
    }

    fun getCreditIssued() {
        val body = if (isFilterMode) {
            val filterBody = filterResult?.toBody()
            CreditIssuedBody(
                farmerId = filterBody?.farmerId,
                date = filterBody?.date,
                rangeBefore = filterBody?.rangeBefore,
                rangeAfter = filterBody?.rangeAfter
            )
        } else {
            CreditIssuedBody(status = creditIssue?.key)
        }
        creditIssuedUseCase.execute(viewModelScope, body) {
            handleResult(it) {
                creditIssuedLV.postValue(it)
            }
        }
    }

    fun itemCLicked(item: IssuedData) {
        selectedCreditId = item.id
        if (isFilterMode) {
            val args =
                IssuedFilterResultFragmentDirections.issuedFilterResultToIssuedDetail(item.id).arguments
            navigateToFragment.startEvent(
                FragmentTransaction(
                    R.id.issued_filter_result_to_issued_detail,
                    args
                )
            )
        } else {
            val args = CreditsFragmentDirections.creditToCreditIssuedDetail(item.id).arguments
            navigateToFragment.startEvent(
                FragmentTransaction(
                    R.id.credit_to_credit_issued_detail,
                    args
                )
            )
        }
    }
}