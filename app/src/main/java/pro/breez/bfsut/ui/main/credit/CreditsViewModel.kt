package pro.breez.bfsut.ui.main.credit

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.domain.interactor.CreditsUseCase
import pro.breez.domain.model.output.CreditLogModel
import javax.inject.Inject

@HiltViewModel
class CreditsViewModel @Inject constructor(
    private val creditsUseCase: CreditsUseCase
) : BaseViewModel() {

    val creditsLV = SingleLiveEvent<List<CreditLogModel>>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getCreditsList()
    }

    private fun getCreditsList() {
        creditsUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                creditsLV.postEvent(list)
            }
        }
    }
}