package pro.breez.bfsut.ui.auth.fragment.auth

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.model.SnackBarMessageOptions
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.domain.interactor.AuthUseCase
import pro.breez.domain.model.input.AuthBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUC: AuthUseCase
) : BaseViewModel() {

    val fieldsError = SingleLiveEvent<Nothing>()

    fun login(authFields: Pair<String?, String?>) {
        val isFieldsEmpty = authFields.first.isNullOrEmpty() && authFields.second.isNullOrEmpty()
        if (isFieldsEmpty) {
            val snackBarBuilder = snackBarBuilder.apply {
                fromOptions(SnackBarMessageOptions.AUTH_FIELDS_ERROR)
                fieldsError.call()
            }
            showSnackBar(snackBarBuilder)
            return
        }
        showLoadingView()
        authUC.execute(viewModelScope, AuthBody(authFields.first!!, authFields.second!!)) {
            handleResult(it) {
                navigateToActivity.startEvent(ActivityTransaction(R.id.auth_fragment_to_main_activity))
            }
        }

    }
}