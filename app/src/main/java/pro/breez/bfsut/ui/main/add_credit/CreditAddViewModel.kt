package pro.breez.bfsut.ui.main.add_credit

import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.bfsut.util.alert.dialog.SearchItemDialog
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.bfsut.util.alert.snackbar.SnackbarNotificationBuilder
import pro.breez.domain.interactor.*
import pro.breez.domain.model.input.CreditBody
import pro.breez.domain.model.input.PeriodBody
import pro.breez.domain.model.output.*
import javax.inject.Inject

@HiltViewModel
open class CreditAddViewModel @Inject constructor(
    private val searchFarmerUseCase: CreditSearchFarmerUseCase,
    private val productUseCase: ProductUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val goalUseCase: GoalUseCase,
    private val postCreditUseCase: CreditUseCase,
    private val periodUseCase: CreditPeriodUseCase,
) : BaseViewModel() {

    val farmerLV = SingleLiveEvent<CreditSearchFarmerModel>()
    val productLV = SingleLiveEvent<ProductsModel?>()
    val categoryLV = SingleLiveEvent<CategoryModel>()
    val goalLV = SingleLiveEvent<GoalModel>()
    val commentOfGoal = SingleLiveEvent<String>()
    val sumLV = SingleLiveEvent<String>()
    val dateOfPaymentLV = SingleLiveEvent<String>()
    val periodLV = SingleLiveEvent<PeriodModel>()
    val dateDisburseLV = SingleLiveEvent<String>()

    fun sendBtnClicked(fieldsNotEmpty: Boolean) {
        if (!fieldsNotEmpty) {
            showErrorSnackbar("Заполните все поля"); return
        }
        showLoadingView()
        val postCreditBody = CreditBody(
            amount = sumLV.value!!,
            category = categoryLV.value!!.id,
            mfsys_customer_id = farmerLV.value!!.customer_id,
            date_pay = dateOfPaymentLV.value!!.toInt(),
            period = periodLV.value!!.inDigit,
            product_bank = productLV.value!!.id,
            purpose_comment = commentOfGoal.value,
            purpose = goalLV.value!!.id,
            date_disburse_plan = DateUtil.reformatDate(dateDisburseLV.value)!!,
            office = farmerLV.value!!.office,
            credit_officer = farmerLV.value!!.credit_specialist_full_name,
        )
        postCreditUseCase.execute(viewModelScope, postCreditBody) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl().apply {
                    setIcon(R.drawable.ic_alert)
                    setTitle("Заявка была принята")
                    setSubTitle("Отличная работа")
                    setDismissListener {
                        previousScreen.trigger()
                    }
                }
                showAlertDialog.startEvent(dialog)
            }
        }
    }

    fun showSearchDialog(isCancelable: Boolean) {
        val dialog =
            SearchItemDialog<CreditSearchFarmerModel>(
                valueName = arrayOf(
                    CreditSearchFarmerModel::father_name.name,
                    CreditSearchFarmerModel::first_name.name,
                    CreditSearchFarmerModel::last_name.name
                )
            )
        dialog.onKeyChanged {
            searchFarmersInSystem(dialog, it)
        }
        dialog.isCancelable = isCancelable

        dialog.notFoundIcon = R.drawable.ic_not_found
        dialog.notFoundText = getString(R.string.credit_farmer_not_found)
        dialog.notFoundBtnText = getString(R.string.create)
        dialog.helperText = getString(R.string.find_by_name_or_tax)
        dialog.onPositiveBtnClicked {
            farmerLV.postValue(it)
        }
        dialog.onHomeBtnClicked {
            popBackStack.trigger()
        }
        dialog.onNotFoundBtnClicked {

        }
        showDialogFragment.startEvent(dialog)
    }

    private fun searchFarmersInSystem(
        dialog: SearchItemDialog<CreditSearchFarmerModel>,
        searchKey: String
    ) {
        searchFarmerUseCase.execute(viewModelScope, searchKey) {
            handleResult(it) {
                dialog.updateList(it)
            }
        }
    }

    fun productClicked() {
        if (goalLV.value == null) {
            showSnackBar.startEvent(
                SnackbarNotificationBuilder()
                    .setMessage("Заполните поле \"цель\" ")
                    .setBackground(R.drawable.bg_error_shape)
            )
            return
        }
        showLoadingView()
        productUseCase.execute(viewModelScope, goalLV.value?.mfsys_id) {
            handleResult(it) { list ->
                val selector = SelectorDialogBuilderImpl<ProductsModel>()
                selector.setList(list)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(ProductsModel::name.name)
                selector.setResultListener {
                    productLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun goalClicked() {
        showLoadingView()
        goalUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val selector = SelectorDialogBuilderImpl<GoalModel>()
                selector.setList(list)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(GoalModel::name.name)
                selector.setResultListener {
                    goalLV.postValue(it)
                    productLV.postValue(null)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun categoryClicked() {
        showLoadingView()
        categoryUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val selector = SelectorDialogBuilderImpl<CategoryModel>()
                selector.setList(list)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(CategoryModel::name.name)
                selector.setResultListener {
                    categoryLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun dateOfPaymentClicked() {
        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .setPositiveButtonText("Подвердить")
            .setNegativeButtonText("Отменить")
            .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            dateOfPaymentLV.postValue(DateUtil.toDay(it))
        }
        showDialogFragment.startEvent(dateRangePicker)
    }

    fun periodClicked() {
        if (productLV.value == null || sumLV.value == null) {
            showSnackBar.startEvent(
                SnackbarNotificationBuilder()
                    .setMessage("Заполните поля \"продукт\" и \"сумма\" ")
                    .setBackground(R.drawable.bg_error_shape)
            )
            return
        }
        showLoadingView()
        val periodBody = PeriodBody(amount = sumLV.value!!, productId = productLV.value!!.mfsys_id)
        periodUseCase.execute(viewModelScope, periodBody) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<PeriodModel>()
                selector.setList(it)
                selector.setSearchByVal(PeriodModel::period.name)
                selector.setVmScope(viewModelScope)
                selector.setResultListener {
                    periodLV.postEvent(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun dateDisburseClicked() {
        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .setPositiveButtonText("Подвердить")
            .setNegativeButtonText("Отменить")
            .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            dateDisburseLV.postValue(DateUtil.toDate(it))
        }
        showDialogFragment.startEvent(dateRangePicker)
    }
}
