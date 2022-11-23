package pro.breez.bfsut.ui.issued_graph

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.domain.interactor.IssuedGraphUseCase
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class IssuedGraphViewModel @Inject constructor(
    private val issuedGraphUseCase: IssuedGraphUseCase
) : BaseViewModel() {

    lateinit var creditId: String

    val graphLV = MutableLiveData<InputStream>()

    fun getIssuedGraph() {
        issuedGraphUseCase.execute(viewModelScope, creditId) {
            handleResult(it) {
                graphLV.postValue(it)
            }
        }
    }

}