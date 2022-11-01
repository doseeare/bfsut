package pro.breez.bfsut.ui.main.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.alert.MilkPriceDialog
import pro.breez.bfsut.util.alert.QuestionDialog
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.data.cache.DataPreference
import pro.breez.data.cache.SettingsPreference
import pro.breez.domain.interactor.ChangeMilkPriceUseCase
import pro.breez.domain.interactor.FarmersUseCase
import pro.breez.domain.interactor.MilkPriceUseCase
import pro.breez.domain.interactor.TotalMilkUseCase
import pro.breez.domain.model.output.FarmersModel
import pro.breez.domain.model.output.MilkPriceModel
import pro.breez.domain.model.output.TotalMilkModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val farmersUseCase: FarmersUseCase,
    private val getTotalMilk: TotalMilkUseCase,
    private val getMilkPrice: MilkPriceUseCase,
    private val changeMilkPrice: ChangeMilkPriceUseCase,
    private val dataPreference: DataPreference,
    val settingsPreference: SettingsPreference
) : BaseViewModel() {

    val farmersLV = MutableLiveData<List<FarmersModel>>()
    val totalMilkLv = MutableLiveData<TotalMilkModel>()
    val milkPriceLV = MutableLiveData<Int>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        showChangePrice()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getFarmers()
        getTotalMilk()
        getMilkPrice()
    }

    private fun showChangePrice() {
        if (settingsPreference.lastPriceChangeDate != DateUtil.getToday()) {
            val dialog = MilkPriceDialog(milkPriceLV.value ?: 0)
            dialog.onPositiveBtnClicked {
                changeMilkPrice(it) {
                    settingsPreference.lastPriceChangeDate = DateUtil.getToday()
                    dialog.dismiss()
                }
            }
            showDialogFragment.startEvent(dialog)
        }
    }

    fun navigateToChangePrice() {
        if (settingsPreference.lastManualPriceChangeDate != DateUtil.getToday()) {
            val dialog = ChangePriceBottomSheetFragment.newInstance(
                milkPriceLV.value ?: 0
            )
            showBottomSheetFragment.startEvent(dialog)
        }
    }

    fun changeMilkPrice(newPrice: Int, block: () -> Unit) {
        changeMilkPrice.execute(viewModelScope, MilkPriceModel(newPrice)) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl()
                dialog.setIcon(R.drawable.ic_success)
                dialog.setTitle("Цена изменена")
                dialog.setSubTitle("")
                dialog.setDismissListener {
                    block.invoke()
                }
                showAlertDialog.startEvent(dialog)
            }
        }
    }

    fun refreshInfo() {
        getMilkPrice()
        getTotalMilk()
    }

    private fun getMilkPrice() {
        getMilkPrice.execute(viewModelScope) {
            handleResult(it) {
                milkPriceLV.postValue(it.price)
            }
        }
    }

    private fun getFarmers() {
        farmersUseCase.execute(viewModelScope) {
            handleResult(it) {
                farmersLV.postValue(it)
            }
        }
    }

    private fun getTotalMilk() {
        getTotalMilk.execute(viewModelScope) {
            handleResult(it) {
                totalMilkLv.postValue(it)
            }
        }
    }

    fun showAll() {
        navigateToFragment.startEvent(FragmentTransaction(R.id.home_fragment_to_all_farmers))
    }

    fun logOut(block: () -> Unit) {
        val dialog = QuestionDialog().apply {
            setTitle("Хотите выйти?")
            onPositiveBtnClicked {
                dataPreference.token = ""
                block.invoke()
            }
        }
        showDialogFragment.startEvent(dialog)
    }

    fun farmerClicked(item: FarmersModel) {
        val args = HomeFragmentDirections.homeFragmentToProfileFarmer(item).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.navigation_farmer_profile, args))
    }

    fun addMilkToFarmer(farmer: FarmersModel) {
        when (farmer.is_picked) {
            "paid" -> {

            }
            "active" -> {
                val args = HomeFragmentDirections.homeFragmentToEditMilk(farmer).arguments
                val transaction = FragmentTransaction(R.id.home_fragment_to_edit_milk, args)
                navigateToFragment.startEvent(transaction)
            }
            "not_active" -> {
                val args = HomeFragmentDirections.homeFragmentToAddMilk(farmer).arguments
                val transaction = FragmentTransaction(R.id.home_fragment_to_add_milk, args)
                navigateToFragment.startEvent(transaction)
            }
        }
    }
}