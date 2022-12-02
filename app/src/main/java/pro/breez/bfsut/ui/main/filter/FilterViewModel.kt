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
import pro.breez.bfsut.model.FilterFromEnum
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

    private val filterFrom: FilterFromEnum by lazy {
        FilterFragmentArgs.fromBundle(requiredArguments()).filterFromEnum
    }

    private var typeFilterSelected: ((isPeriod: Boolean) -> Unit)? = null

    val farmerLV = MutableLiveData<FarmersModel>()
    val rangeDateLv = MutableLiveData<DateRange?>()

    private var filterSpan = FilterSpan.NONE

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        typeFilterSelected = { isPeriod ->
            if (isPeriod) {
                filterSpan = FilterSpan.NONE
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
            .setSelection(if (isFrom) selectedRange?.startMillis else selectedRange?.endMillis)
            .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            val result = if (isFrom) {
                DateRange(
                    start = DateUtil.toDate(it),
                    end = selectedRange?.end,
                    startMillis = it,
                    endMillis = selectedRange?.endMillis
                )
            } else {
                DateRange(
                    start = selectedRange?.start,
                    end = DateUtil.toDate(it),
                    startMillis = selectedRange?.startMillis,
                    endMillis = it
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
        when (filterFrom) {
            FilterFromEnum.LOGS -> {
                val args =
                    FilterFragmentDirections.filterFragmentToFilterResult(filterResult).arguments
                navigateToFragment.startEvent(
                    FragmentTransaction(
                        R.id.navigation_filter_result,
                        args
                    )
                )
            }
            FilterFromEnum.ISSUED_CREDITS -> {
                val args =
                    FilterFragmentDirections.filterFragmentToIssuedFilterResult(filterResult).arguments
                navigateToFragment.startEvent(
                    FragmentTransaction(
                        R.id.navigation_issued_filter_result,
                        args
                    )
                )
            }
        }

    }
}