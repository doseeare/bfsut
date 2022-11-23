package pro.breez.bfsut.ui.main.credit_issued_detail

import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CreditIssuedDetailViewModel @Inject constructor() : BaseViewModel() {

     val creditId: String by lazy {
        CreditIssuedDetailFragmentArgs.fromBundle(requiredArguments()).creditId
    }
}