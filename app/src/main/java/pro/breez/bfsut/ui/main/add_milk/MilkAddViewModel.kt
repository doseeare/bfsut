package pro.breez.bfsut.ui.main.add_milk

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.domain.interactor.MilkPriceUseCase
import pro.breez.domain.model.output.MilkPriceModel
import javax.inject.Inject

@HiltViewModel
class MilkAddViewModel @Inject constructor(
    private val milkPriceUseCase: MilkPriceUseCase
) : BaseViewModel() {

    val milkPriceLV = MutableLiveData<MilkPriceModel>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        milkPriceUseCase.execute(viewModelScope) {
            handleResult(it) { milkPrice ->
                milkPriceLV.postValue(milkPrice)
            }
        }
    }

    fun createBtnClicked() {

    }
}