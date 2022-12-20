package pro.breez.bfsut.ui.main.credit

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.FilterFromEnum
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.home.HomeFragmentDirections
import pro.breez.bfsut.util.alert.dialog.SearchItemDialog
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.FarmersUseCase
import pro.breez.domain.interactor.SearchFarmerUseCase
import pro.breez.domain.model.output.FarmersModel
import pro.breez.domain.model.output.MfSysFarmerModel
import javax.inject.Inject

@HiltViewModel
class CreditsViewModel @Inject constructor(
    private val farmersUseCase: FarmersUseCase,
    private val searchFarmer: SearchFarmerUseCase
) : BaseViewModel() {

    var isCredit = true

    fun filterClicked() {
        if (isCredit)
            searchFarmers()
        else
            filterIssued()
    }

    private fun filterIssued() {
        val args =
            CreditsFragmentDirections.creditsFragmentToFilter(FilterFromEnum.ISSUED_CREDITS).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.credits_fragment_to_filter, args))
    }

    private fun searchFarmers() {
        showLoadingView()
        farmersUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val selector = SelectorDialogBuilderImpl<FarmersModel>()
                selector.setList(list)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(FarmersModel::full_name.name)
                selector.setResultListener {
                    navigateToSearchResult(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    private fun navigateToSearchResult(farmer: FarmersModel) {
        val args = CreditsFragmentDirections.creditToCreditsSearchResult(farmer).arguments
        navigateToFragment.startEvent(
            FragmentTransaction(
                R.id.credit_to_credits_search_result,
                args
            )
        )
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