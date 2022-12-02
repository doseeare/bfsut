package pro.breez.bfsut.ui.main.add_milk

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.util.alert.QuestionDialog
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.AddMilkUseCase
import pro.breez.domain.interactor.FarmersCheckUseCase
import pro.breez.domain.interactor.MilkPriceUseCase
import pro.breez.domain.interactor.base.EveningStatusUseCase
import pro.breez.domain.model.input.AddMilkBody
import pro.breez.domain.model.output.FarmerCheckModel
import pro.breez.domain.model.output.FarmersModel
import pro.breez.domain.model.output.LogsModel
import pro.breez.domain.model.output.MilkPriceModel
import javax.inject.Inject

@HiltViewModel
class MilkAddViewModel @Inject constructor(
    private val milkPriceUseCase: MilkPriceUseCase,
    private val farmersCheckUseCase: FarmersCheckUseCase,
    private val addMilkUseCase: AddMilkUseCase,
    private val eveningStatusUseCase: EveningStatusUseCase,
) : BaseViewModel() {

    val milkPriceLV = SingleLiveEvent<MilkPriceModel>()
    val eveningStatusLV = SingleLiveEvent<Boolean>()
    val farmerLV = SingleLiveEvent<FarmerCheckModel>()
    val isSelectedFarmer = SingleLiveEvent<Boolean>()

    private val farmer: FarmerCheckModel? by lazy {
        MilkAddFragmentArgs.fromBundle(requiredArguments()).farmer
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        if (farmer != null) {
            farmerLV.postValue(farmer)
            isSelectedFarmer.postValue(true)
        } else {
            isSelectedFarmer.postValue(false)
        }
        milkPriceUseCase.execute(viewModelScope) {
            handleResult(it) { milkPrice ->
                milkPriceLV.postValue(milkPrice)
            }
        }
        eveningStatusUseCase.execute(viewModelScope) {
            handleResult(it) {
                eveningStatusLV.postValue(it.status)
            }
        }
    }

    fun createBtnClicked(morning: String, evening: String) {
        val body = AddMilkBody(
            farmer = farmerLV.value!!.id,
        )
        if (morning.isNotBlank()) body.morning = morning
            .filter { it.isDigit() }.toInt()
        if (evening.isNotBlank()) body.evening = evening
            .filter { it.isDigit() }.toInt()

        addMilkUseCase.execute(viewModelScope, body) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl().apply {
                    setIcon(R.drawable.ic_milk_icon)
                    setTitle(getString(R.string.milk_accept))
                    setSubTitle(getString(R.string.thank_for_job))
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
        farmersCheckUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val selector = SelectorDialogBuilderImpl<FarmerCheckModel>()
                selector.setList(list)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(FarmersModel::full_name.name)
                selector.setResultListener {
                    checkFarmer(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    private fun checkFarmer(farmer: FarmerCheckModel) {
        if (farmer.report_id != null) {
            val title = getString(R.string.question_edit_log)
            val dialog = QuestionDialog().apply {
                setTitle(title)
                onPositiveBtnClicked {
                    navigateToCalc(farmer, this)
                }
            }
            showDialogFragment.startEvent(dialog)
        } else {
            farmerLV.postValue(farmer)
        }
    }

    private fun navigateToCalc(farmer: FarmerCheckModel, dialog: QuestionDialog) {
        val logs = LogsModel(
            id = farmer.report_id!!,
            agent = 0,
            date = "",
            evening = farmer.evening,
            farmer_name = farmer.full_name,
            morning_price = farmer.morning_price,
            evening_price = farmer.evening_price,
            morning = farmer.morning,
            overall = "",
            status = "active",
            paid_date = null,
            isFilter = false,
            isSelected = false
        )
        dialog.dismiss()
        val args = MilkAddFragmentDirections.milkAddToCalc(logs).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.calculate_active_logs, args))
    }
}