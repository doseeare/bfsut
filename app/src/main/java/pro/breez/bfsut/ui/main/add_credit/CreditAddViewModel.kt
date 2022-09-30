package pro.breez.bfsut.ui.main.add_credit

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.*
import pro.breez.domain.model.input.CreditModelIn
import pro.breez.domain.model.output.CategoryModelOut
import pro.breez.domain.model.output.FarmersModelOut
import pro.breez.domain.model.output.GoalModelOut
import pro.breez.domain.model.output.ProductsModelOut
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

    val farmerLV = SingleLiveEvent<FarmersModelOut>()
    val productLV = SingleLiveEvent<ProductsModelOut>()
    val categoryLV = SingleLiveEvent<CategoryModelOut>()
    val goalLV = SingleLiveEvent<GoalModelOut>()
    val dateLV = SingleLiveEvent<Pair<String, String>>()

    val commentOfGoal = SingleLiveEvent<String>()
    val dateOfPayment = SingleLiveEvent<String>()
    val sum = SingleLiveEvent<String>()

    fun sendBtnClicked(fieldsNotEmpty: Boolean) {
        if (!fieldsNotEmpty) {
            showErrorSnackbar("Заполните все поля"); return
        }

        val postCreditBody = CreditModelIn(
            amount = sum.value!!,
            category = categoryLV.value!!.id,
            customer = farmerLV.value!!.id,
            date_pay = dateOfPayment.value!!.toInt(),
            period = 12,
            product_bank = productLV.value!!.id,
            purpose_comment = commentOfGoal.value!!,
            purpose = goalLV.value!!.id,
            date_disburse_plan = "2022-09-27" //поменять когда подключат
        )
        postCreditUseCase.execute(viewModelScope, postCreditBody) {
            handleResult(it) {
                popBackStack.startEvent(R.id.navigation_home)
            }
        }
    }

    fun farmerClicked() {
        showLoadingView()
        farmersUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val textFieldList =
                    list.map { "${it.first_name} ${it.father_name} ${it.last_name}" }
                val selector = SelectorDialogBuilderImpl(list, textFieldList) { selectedFarmer ->
                    farmerLV.postValue(selectedFarmer)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }


    fun productClicked() {
        showLoadingView()
        productUseCase.execute(viewModelScope, goalLV.value?.mfsys_id) {
            handleResult(it) { list ->
                val textFieldList =
                    list.map { it.name }
                val selector = SelectorDialogBuilderImpl(list, textFieldList) { selectedProduct ->
                    productLV.postValue(selectedProduct)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun goalClicked() {
        showLoadingView()
        goalUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val textFieldList =
                    list.map { it.name }
                val selector = SelectorDialogBuilderImpl(list, textFieldList) { selectedProduct ->
                    goalLV.postValue(selectedProduct)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun categoryClicked() {
        showLoadingView()
        categoryUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val textFieldList =
                    list.map { it.name }
                val selector = SelectorDialogBuilderImpl(list, textFieldList) { selectedProduct ->

                    categoryLV.postValue(selectedProduct)
                }
                showSelectorDialog.startEvent(selector)
            }
        }

    }

    //todo поменять на список из сервара, когда будет
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
    }


}