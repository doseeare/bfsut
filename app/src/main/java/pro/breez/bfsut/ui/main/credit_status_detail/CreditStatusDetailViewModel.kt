package pro.breez.bfsut.ui.main.credit_status_detail

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.domain.interactor.CreditDetailUseCase
import pro.breez.domain.model.output.CreditDetailModel
import javax.inject.Inject

@HiltViewModel
class CreditStatusDetailViewModel @Inject constructor(
    private val creditDetailUseCase: CreditDetailUseCase
) : BaseViewModel() {

    private val creditId: String by lazy {
        CreditStatusDetailFragmentArgs.fromBundle(requiredArguments()).creditId
    }

    val creditDetailLV = MutableLiveData<CreditDetailModel>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getCreditDetail()
    }

    private fun getCreditDetail() {
        showLoadingView()
        creditDetailUseCase.execute(viewModelScope, creditId) {
            handleResult(it) {
                creditDetailLV.postValue(it)
            }
        }
    }

}