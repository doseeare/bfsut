package pro.breez.bfsut.ui.issued_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.domain.interactor.IssuedDetailUseCase
import pro.breez.domain.model.output.IssuedDetailModel
import javax.inject.Inject

@HiltViewModel
class IssuedInfoViewModel @Inject constructor(
    private val issuedDetailUseCase: IssuedDetailUseCase
) : BaseViewModel() {
    lateinit var creditId: String

    val issuedDetailLV = MutableLiveData<IssuedDetailModel>()

    fun getIssuedDetail() {
        showLoadingView()
        issuedDetailUseCase.execute(viewModelScope, creditId) {
            handleResult(it) {
                issuedDetailLV.postValue(it)
            }
        }
    }
}