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
import pro.breez.bfsut.util.alert.dialog.SearchItemDialog
import pro.breez.bfsut.util.alert.snackbar.SnackbarNotificationBuilder
import pro.breez.data.cache.DataPreference
import pro.breez.data.cache.SettingsPreference
import pro.breez.domain.interactor.*
import pro.breez.domain.model.output.FarmerCheckModel
import pro.breez.domain.model.output.MfSysFarmerModel
import pro.breez.domain.model.output.MilkPriceModel
import pro.breez.domain.model.output.TotalMilkModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val farmerCheck: FarmersCheckUseCase,
    private val getTotalMilk: TotalMilkUseCase,
    private val getMilkPrice: MilkPriceUseCase,
    private val changeMilkPrice: ChangeMilkPriceUseCase,
    private val unreadCreditCountUseCase: UnreadCreditCountUseCase,
    private val settingsPreference: SettingsPreference,
    private val searchFarmer: SearchFarmerUseCase,
    val dataPreference: DataPreference
) : BaseViewModel() {

    val farmersCheckLV = MutableLiveData<ArrayList<FarmerCheckModel>>()
    val totalMilkLv = MutableLiveData<TotalMilkModel>()
    val milkPriceLV = MutableLiveData<Int>()
    val userNameLV = MutableLiveData<String>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        showChangePrice()
        getUnreadCount()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        getFarmersCheck()
        getTotalMilk()
        getMilkPrice()
    }

    private fun getUnreadCount() {
        unreadCreditCountUseCase.execute(viewModelScope) {
            handleResult(it) {
                badge.postEvent(it.amount)
            }
        }
    }

    private fun showChangePrice() {
        getMilkPrice {
            if (settingsPreference.lastPriceChangeDate != DateUtil.getToday()) {
                val dialog = MilkPriceDialog(it)
                dialog.onPositiveBtnClicked {
                    dialog.dismiss()
                    showLoadingView()
                    changeMilkPrice(it)
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
        } else {
            val snackbarBuilder =
                SnackbarNotificationBuilder().setMessageColor(R.color.text_bold_color).setMessage(
                    "Цену на молоко можно изменять только раз \n" +
                            "за день. Вы ее уже меняли "
                ).setBackground(R.drawable.blue_snackbar)

            showSnackBar.startEvent(snackbarBuilder)
        }
    }

    fun changeMilkPrice(newPrice: Int, block: (() -> Unit)? = null) {
        changeMilkPrice.execute(viewModelScope, MilkPriceModel(newPrice)) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl()
                dialog.setIcon(R.drawable.ic_success)
                dialog.setTitle("Цена изменена")
                dialog.setSubTitle("")
                dialog.setDismissListener {
                    if (block == null)
                        settingsPreference.lastPriceChangeDate = DateUtil.getToday()
                    else
                        settingsPreference.lastManualPriceChangeDate = DateUtil.getToday()
                    block?.invoke()
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
                if (it.isNotEmpty()) {
                    farmersCheckLV.postValue(it as ArrayList)
                } else {
                    farmersCheckLV.postValue(arrayListOf())
                }
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
            setTitle("Хотите выйти\n из приложения?")
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

    private fun showSearchDialog(isCancelable: Boolean) {
        val dialog =
            SearchItemDialog<MfSysFarmerModel>(
                valueName = arrayOf(
                    MfSysFarmerModel::fatherName.name,
                    MfSysFarmerModel::firstName.name,
                    MfSysFarmerModel::lastName.name
                )
            )
        dialog.onKeyChanged {
            searchFarmersInSystem(dialog, it)
        }
        dialog.isCancelable = isCancelable
        dialog.notFoundBtnText = "Создать фермера"
        dialog.isHomeBtnGone = false
        dialog.homeBtnText = "Вернуться назад"
        dialog.onNotFoundBtnClicked {
            navigateToAddFarmer(null)
        }
        dialog.onHomeBtnClicked {
            popBackStack.trigger()
        }
        dialog.onPositiveBtnClicked {
            navigateToAddFarmer(it)
        }
        showDialogFragment.startEvent(dialog)
    }

    private fun navigateToAddFarmer(farmer: MfSysFarmerModel?) {
        val args = HomeFragmentDirections.fragmentHomeToAddFarmer(farmer).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.navigation_farmer_add, args))
    }

    private fun searchFarmersInSystem(
        dialog: SearchItemDialog<MfSysFarmerModel>,
        searchKey: String
    ) {
        searchFarmer.execute(viewModelScope, searchKey) {
            handleResult(it) {
                dialog.updateList(it)
            }
        }
    }

    fun navigateToCreditAdd() {
        showSearchDialog(true)
    }
}