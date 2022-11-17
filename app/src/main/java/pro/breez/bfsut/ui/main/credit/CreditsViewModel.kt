package pro.breez.bfsut.ui.main.credit

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.FarmersUseCase
import pro.breez.domain.model.output.FarmersModel
import javax.inject.Inject

@HiltViewModel
class CreditsViewModel @Inject constructor(
    private val farmersUseCase: FarmersUseCase,
    ) : BaseViewModel() {

    var isCredit = true

    fun filterClicked() {
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
}