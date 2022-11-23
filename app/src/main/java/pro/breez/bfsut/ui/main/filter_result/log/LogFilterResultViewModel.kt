package pro.breez.bfsut.ui.main.filter_result.log

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.FilterResult
import javax.inject.Inject

@HiltViewModel
class LogFilterResultViewModel @Inject constructor() : BaseViewModel() {

    val filterResult: FilterResult by lazy {
        LogFilterResultFragmentArgs.fromBundle(requiredArguments()).filterResult
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d("FilterResultViewModel", "filterResult: $filterResult")
    }

}