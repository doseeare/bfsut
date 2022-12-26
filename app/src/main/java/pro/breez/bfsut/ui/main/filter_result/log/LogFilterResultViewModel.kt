package pro.breez.bfsut.ui.main.filter_result.log

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.FilterResult
import pro.breez.bfsut.model.FilterSpan
import pro.breez.bfsut.util.ifNotNull
import javax.inject.Inject

@HiltViewModel
class LogFilterResultViewModel @Inject constructor() : BaseViewModel() {

    val filterResult: FilterResult by lazy {
        LogFilterResultFragmentArgs.fromBundle(requiredArguments()).filterResult
    }

    val filterTitleLV = MutableLiveData<String>()

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        val filterTitle = buildString {
            filterResult.farmerName.ifNotNull {
                append(it)
            }
            filterResult.range.ifNotNull {
                filterResult.farmerName.ifNotNull { append("\n\n") }
                append("${it.start} - ${it.end}")
            }
            if (filterResult.filterSpan != FilterSpan.NONE ) {
                filterResult.farmerName.ifNotNull { append("\n\n") }
                append(filterResult.filterSpan?.title)
            }
        }
        filterTitleLV.postValue(filterTitle)
        Log.d("FilterResultViewModel", "filterResult: $filterResult")
    }

}