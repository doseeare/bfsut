package pro.breez.bfsut.ui.main.add_milk

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.AddMilkUseCase
import pro.breez.domain.interactor.FarmersUseCase
import pro.breez.domain.interactor.MilkPriceUseCase
import pro.breez.domain.model.input.AddMilkBody
import pro.breez.domain.model.output.FarmersModel
import pro.breez.domain.model.output.MilkPriceModel
import javax.inject.Inject

@HiltViewModel
class MilkAddViewModel @Inject constructor(
    private val milkPriceUseCase: MilkPriceUseCase,
    private val farmersUseCase: FarmersUseCase,
    private val addMilkUseCase: AddMilkUseCase,
) : BaseViewModel() {

    val milkPriceLV = MutableLiveData<MilkPriceModel>()
    val farmerLV = MutableLiveData<FarmersModel>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        milkPriceUseCase.execute(viewModelScope) {
            handleResult(it) { milkPrice ->
                milkPriceLV.postValue(milkPrice)
            }
        }
    }

    fun createBtnClicked(morning: String, evening: String) {
        val body = AddMilkBody(
            farmer = farmerLV.value!!.id,
            evening = evening.toInt(),
            morning = morning.toInt()
        )
        addMilkUseCase.execute(viewModelScope, body) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl().apply {
                    setIcon(R.drawable.ic_milk_icon)
                    setTitle("Молоко принято")
                    setSubTitle("Спасибо за работа")
                    setDismissListener {
                        previousScreen.trigger()
                    }
                }
                showAlertDialog.startEvent(dialog)
            }
        }
    }

    fun farmerClicked() {
        showLoadingView()
        farmersUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val selector = SelectorDialogBuilderImpl<FarmersModel>()
                selector.setList(list)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(FarmersModel::full_name.name)
                selector.setResultListener {
                    farmerLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }
}