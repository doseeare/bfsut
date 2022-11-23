package pro.breez.bfsut.ui.main.log

import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.FilterFromEnum
import pro.breez.bfsut.model.navigation.FragmentTransaction

class LogViewModel : BaseViewModel() {

    fun filterClicked() {
        val args = LogFragmentDirections.logFragmentToFilter(FilterFromEnum.LOGS).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.log_fragment_to_filter, args))
    }

}