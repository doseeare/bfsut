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
import pro.breez.domain.interactor.FarmersCheckUseCase
import pro.breez.domain.interactor.MilkPriceUseCase
import pro.breez.domain.interactor.TotalMilkUseCase
import pro.breez.domain.model.output.FarmerCheckModel
import pro.breez.domain.model.output.MilkPriceModel
import pro.breez.domain.model.output.TotalMilkModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val farmerCheck: FarmersCheckUseCase,
    private val getTotalMilk: TotalMilkUseCase,
    private val getMilkPrice: MilkPriceUseCase,
    private val changeMilkPrice: ChangeMilkPriceUseCase,
    val dataPreference: DataPreference,
    val settingsPreference: SettingsPreference
) : BaseViewModel() {

    val farmersCheckLV = MutableLiveData<List<FarmerCheckModel>>()
    val totalMilkLv = MutableLiveData<TotalMilkModel>()
    val milkPriceLV = MutableLiveData<Int>()
    val userNameLV = MutableLiveData<String>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        showChangePrice()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getFarmersCheck()
        getTotalMilk()
        getMilkPrice()
    }

    private fun showChangePrice() {
        getMilkPrice {
            if (settingsPreference.lastPriceChangeDate != DateUtil.getToday()) {
                val dialog = MilkPriceDialog(it)
                dialog.onPositiveBtnClicked {
                    changeMilkPrice(it) {
                        settingsPreference.lastPriceChangeDate = DateUtil.getToday()
                        dialog.dismiss()
                    }
                }
                showDialogFragment.startEvent(dialog)
            }
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

    private fun getMilkPrice(block: ((Int) -> Unit)? = null) {
        getMilkPrice.execute(viewModelScope) {
            handleResult(it) {
                block?.invoke(it.price)
                milkPriceLV.postValue(it.price)
            }
        }
    }

    private fun getFarmersCheck() {
        farmerCheck.execute(viewModelScope) {
            handleResult(it) {
                farmersCheckLV.postValue(it)
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
                dataPreference.userName = ""
                block.invoke()
            }
        }
        showDialogFragment.startEvent(dialog)
    }

    fun farmerClicked(item: FarmerCheckModel) {
        val args = HomeFragmentDirections.homeFragmentToProfileFarmer(item.id).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.navigation_farmer_profile, args))
    }

    fun addMilkToFarmer(farmer: FarmerCheckModel) {
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