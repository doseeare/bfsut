package pro.breez.bfsut.ui.main.credit

import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.databinding.FragmentCreditIssuedTabBinding
import javax.inject.Inject

@AndroidEntryPoint
class CreditIssuedTabFragment :
    BaseFragment<FragmentCreditIssuedTabBinding, CreditIssueTabViewModel>() {


}

@HiltViewModel
class CreditIssueTabViewModel @Inject constructor() : BaseViewModel()