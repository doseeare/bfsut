package pro.breez.bfsut.ui.main.add

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.*
import pro.breez.domain.model.input.CreditModelIn
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

    val farmerLV = SingleLiveEvent<Pair<String, String>>()
    val productLV = SingleLiveEvent<Pair<String, String>>()
    val categoryLV = SingleLiveEvent<Pair<String, String>>()
    val goalLV = SingleLiveEvent<Pair<String, String>>()
    val dateLV = SingleLiveEvent<Pair<String, String>>()

    val commentOfGoal = SingleLiveEvent<String>()
    val dateOfPayment = SingleLiveEvent<String>()
    val sum = SingleLiveEvent<String>()

    fun sendBtnClicked() {
        val postCreditBody = CreditModelIn(
            amount = sum.value!!,
            category = categoryLV.value!!.first.toInt(),
            customerID = farmerLV.value!!.first,
            datePay = 0,
            period = 0,
            productBankID = productLV.value!!.first.toInt(),
            purposeComment = commentOfGoal.value!!,
            purposeID = goalLV.value!!.first.toInt()
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
                val selector = SelectorDialogBuilderImpl(list) { selectedFarmer ->
                    farmerLV.postValue(selectedFarmer)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }


    fun productClicked() {
        showLoadingView()
        productUseCase.execute(viewModelScope) {
            handleResult(it) { list ->
                val selector = SelectorDialogBuilderImpl(list) { selectedProduct ->
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
                val selector = SelectorDialogBuilderImpl(list) { selectedProduct ->
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
                val selector = SelectorDialogBuilderImpl(list) { selectedProduct ->
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
        val selector = SelectorDialogBuilderImpl(list) { selectedProduct ->
            dateLV.postValue(selectedProduct)
        }
        showSelectorDialog.startEvent(selector)
    }


}