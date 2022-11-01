package pro.breez.bfsut.ui.main.add_credit

import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.*
import pro.breez.domain.model.input.CreditBody
import pro.breez.domain.model.output.CategoryModel
import pro.breez.domain.model.output.FarmersModel
import pro.breez.domain.model.output.GoalModel
import pro.breez.domain.model.output.ProductsModel
import javax.inject.Inject

@HiltViewModel
open class CreditAddViewModel @Inject constructor(
    private val farmersUseCase: FarmersUseCase,
    private val productUseCase: ProductUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val dateUseCase: DateUseCase,
    private val goalUseCase: GoalUseCase,
    private val postCreditUseCase: CreditUseCase

) : BaseViewModel() {

    val farmerLV = SingleLiveEvent<FarmersModel>()
    val productLV = SingleLiveEvent<ProductsModel>()
    val categoryLV = SingleLiveEvent<CategoryModel>()
    val goalLV = SingleLiveEvent<GoalModel>()

    val commentOfGoal = SingleLiveEvent<String>()
    val sum = SingleLiveEvent<String>()
    val dateOfPaymentLV = SingleLiveEvent<String>()
    val periodLV = SingleLiveEvent<String>()

    fun sendBtnClicked(fieldsNotEmpty: Boolean) {
        if (!fieldsNotEmpty) {
            showErrorSnackbar("Заполните все поля"); return
        }

        val postCreditBody = CreditBody(
            amount = sum.value!!,
            category = categoryLV.value!!.id,
            customer = farmerLV.value!!.id,
            date_pay = 1,
            period = 1,
            product_bank = productLV.value!!.id,
            purpose_comment = commentOfGoal.value!!,
            purpose = goalLV.value!!.id,
            date_disburse_plan = "2022-12-12"
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


    fun productClicked() {
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
            dateOfPaymentLV.postValue(DateUtil.toDate(it))
        }
        showDialogFragment.startEvent(dateRangePicker)
    }

    fun periodClicked() {
        val list = ArrayList<TestModel>()
        list.add(TestModel("3 месяца"))
        list.add(TestModel("6 месяцев"))
        list.add(TestModel("9 месяцев"))
        list.add(TestModel("12 месяцев"))
        val selector = SelectorDialogBuilderImpl<TestModel>()
        selector.setList(list)
        selector.setSearchByVal(TestModel::name.name)
        selector.setVmScope(viewModelScope)
        selector.setResultListener {
            periodLV.postEvent(it.name)
        }
        showSelectorDialog.startEvent(selector)
    }

/*    //todo поменять на список из сервара, когда будет
    fun dateClicked() {
        val list = ArrayList<Pair<String, String>>()
        list.add("1" to "3 месяца")
        list.add("1" to "6 месяцев")
        list.add("1" to "9 месяцев")
        list.add("1" to "12 месяцев")
        val textField = list.map { it.second }
        val selector = SelectorDialogBuilderImpl(list, textField) { selectedProduct ->
            dateLV.postValue(selectedProduct)
        }
        showSelectorDialog.startEvent(selector)
    }*/
}

class TestModel(val name: String)
