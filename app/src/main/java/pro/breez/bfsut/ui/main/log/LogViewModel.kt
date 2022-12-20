package pro.breez.bfsut.ui.main.log

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.FilterFromEnum
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.home.HomeFragmentDirections
import pro.breez.bfsut.util.alert.dialog.SearchItemDialog
import pro.breez.domain.interactor.SearchFarmerUseCase
import pro.breez.domain.model.output.MfSysFarmerModel
import javax.inject.Inject

@HiltViewModel
class LogViewModel @Inject constructor(
    private val searchFarmer: SearchFarmerUseCase
) : BaseViewModel() {

    fun filterClicked() {
        val args = LogFragmentDirections.logFragmentToFilter(FilterFromEnum.LOGS).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.log_fragment_to_filter, args))
    }

    private fun showSearchDialog(isCancelable: Boolean) {
        val dialog =
            SearchItemDialog<MfSysFarmerModel>(
                valueName = arrayOf(
                    MfSysFarmerModel::firstName.name,
                    MfSysFarmerModel::fatherName.name,
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