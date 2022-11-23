package pro.breez.bfsut.ui.main.filter_result.issued

import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.FilterResult
import javax.inject.Inject

@HiltViewModel
class IssuedFilterResultViewModel @Inject constructor() : BaseViewModel() {

    val filterResult: FilterResult by lazy {
        IssuedFilterResultFragmentArgs.fromBundle(requiredArguments()).filterResult
    }

}