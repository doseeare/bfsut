package pro.breez.bfsut.ui.main.filter

import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.DateRange
import pro.breez.bfsut.model.FilterResult
import pro.breez.bfsut.model.FilterSpan
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.FarmersUseCase
import pro.breez.domain.model.output.FarmersModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val farmersUseCase: FarmersUseCase,
) : BaseViewModel() {

    private var typeFilterSelected: ((isPeriod: Boolean) -> Unit)? = null

    val farmerLV = MutableLiveData<FarmersModel>()
    val rangeDateLv = MutableLiveData<DateRange?>()

    private var filterSpan = FilterSpan.ALL_TIME

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        typeFilterSelected = { isPeriod ->
            if (isPeriod) {
                filterSpan = FilterSpan.ALL_TIME
            } else {
                rangeDateLv.postValue(null)
            }
        }
    }

    fun backBtnClicked() {
        popBackStack.startEvent(null)
    }

    fun farmerClicked() {
        showLoadingView()
        farmersUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val selector = SelectorDialogBuilderImpl<FarmersModel>()
                selector.setList(list)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(FarmersModel::full_name.name)
                selector.setResultListener {
                    farmerLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun spanSelected(@IdRes id: Int, isResetClicked: Boolean) {
        filterSpan = FilterSpan.getFromId(id)
        if (isResetClicked) return
        typeFilterSelected?.invoke(false)
    }

    fun showDatePicker(isFrom: Boolean) {
        val selectedRange = rangeDateLv.value
        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите период")
            .setPositiveButtonText("Подвердить")
            .setNegativeButtonText("Отменить")
            .setSelection(if (isFrom) selectedRange?.fromMillis else selectedRange?.toMillis)
            .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            val result = if (isFrom) {
                DateRange(
                    from = DateUtil.toDate(it),
                    to = selectedRange?.to,
                    fromMillis = it,
                    toMillis = selectedRange?.toMillis
                )
            } else {
                DateRange(
                    from = selectedRange?.from,
                    to = DateUtil.toDate(it),
                    fromMillis = selectedRange?.fromMillis,
                    toMillis = it
                )
            }
            rangeDateLv.postValue(result)
            typeFilterSelected?.invoke(true)
        }
        showDialogFragment.postValue(dateRangePicker)
    }

    fun acceptClicked() {
        val filterResult = FilterResult(
            farmerLV.value?.full_name,
            farmerLV.value?.id,
            filterSpan,
            rangeDateLv.value
        )
        val args = FilterFragmentDirections.filterFragmentToFilterResult(filterResult).arguments
        navigateToFragment.startEvent(FragmentTransaction(R.id.navigation_filter_result, args))
    }
}