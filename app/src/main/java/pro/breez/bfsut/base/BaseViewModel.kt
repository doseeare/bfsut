package pro.breez.bfsut.base

import android.content.Intent
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pro.breez.bfsut.R
import pro.breez.bfsut.exception.ErrorMessageFactory
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.model.SnackBarMessageOptions
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.util.alert.DialogBuilder
import pro.breez.bfsut.util.alert.LoadingViewParams
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilder
import pro.breez.bfsut.util.alert.snackbar.SnackbarNotificationBuilder
import pro.breez.bfsut.util.alert.snackbar.SnackbarNotificationBuilderInterface
import pro.breez.bfsut.util.permission.PermissionCheckerBuilder
import pro.breez.domain.exception.ConnectionLostException
import pro.breez.domain.interactor.base.CompletableResult
import pro.breez.domain.interactor.base.Result

open class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    val setToast = SingleLiveEvent<Pair<String, Boolean>>()
    val showSnackBar = SingleLiveEvent<SnackbarNotificationBuilderInterface>()
    val showLoadingView = MutableLiveData<LoadingViewParams>()
    val dialogBuilderEvent = SingleLiveEvent<DialogBuilder>()
    val permissionCheckerEvent = SingleLiveEvent<PermissionCheckerBuilder>()
    val showBottomSheetFragment = SingleLiveEvent<BottomSheetDialogFragment>()
    val showSelectorDialog = SingleLiveEvent<SelectorDialogBuilder>()
    val navigateToFragment = SingleLiveEvent<FragmentTransaction>()
    val navigateToActivity = SingleLiveEvent<ActivityTransaction>()
    val handleIntent = SingleLiveEvent<Intent>()
    val popBackStack = SingleLiveEvent<Int>()
    val finish = SingleLiveEvent<Void>()
    val onNetworkConnectionLostAlert = SingleLiveEvent<Void>()
    val onNetworkConnectionRestored = SingleLiveEvent<Void>()

    val snackBarBuilder = SnackbarNotificationBuilder()
    lateinit var getStringResource: (Int, Array<out Any?>?) -> String

    fun showSnackBar(builderInterface: SnackbarNotificationBuilderInterface) {
        showSnackBar.startEvent(builderInterface)
    }

    fun onCoroutinesFailed(
        throwable: Throwable,
        defaultErrorMessage: String = getString(R.string.userFriendly_errorMessage),
        @DrawableRes iconDrawable: Int? = null
    ) {
        throwable.printStackTrace()
        if (showConnectionLostExceptionIfNeeded(throwable)) return

        val errorMessage = ErrorMessageFactory.create(throwable) ?: defaultErrorMessage
        showErrorSnackbar(errorMessage, iconDrawable)
    }

    fun showErrorSnackbar(message: String, @DrawableRes icon: Int? = null) {
        val snackbar = SnackbarNotificationBuilder().apply {
            setBackground(R.drawable.bg_snackbar_error)
            if (icon != null) setLeftIcon(icon)
            setMessage(message)
            setMessageColor(R.color.white)
        }
        showSnackBar(snackbar)
    }

    fun showSnackBar(snackBarMessageOptions: SnackBarMessageOptions) {
        val snackbarBuilder = SnackbarNotificationBuilder().fromOptions(snackBarMessageOptions)
        showSnackBar.startEvent(snackbarBuilder)
    }

    fun showLoadingView(@StringRes message: Int = R.string.progress_bar_status_text) {
        showLoadingView.postValue(LoadingViewParams(true, message))
    }

    fun hideLoadingView() {
        showLoadingView.postValue(LoadingViewParams(false))
    }

    fun handleResult(completableResult: CompletableResult, onSuccess: () -> Unit) {
        completableResult.perform({
            onSuccess()
        }, {
            hideLoadingView()
            onCoroutinesFailed(it)
        })
    }

    fun <T> handleResult(result: Result<T>, onSuccess: (T) -> Unit) {
        if (result.networkConnectionEnable) {
            onNetworkConnectionRestored.call()
        }
        result.perform({
            onSuccess(it)
            hideLoadingView()
        }, {
            onCoroutinesFailed(it)
            hideLoadingView()
        })
    }

    protected fun getString(@StringRes resourceId: Int, vararg params: Any?): String {
        return getStringResource(resourceId, params)
    }

    @CallSuper
    open fun onConnectionLost() {
        onNetworkConnectionLostAlert.call()
    }

    private fun showConnectionLostExceptionIfNeeded(throwable: Throwable): Boolean {
        if (throwable is ConnectionLostException) {
            onConnectionLost()
            return true
        }
        return false
    }
}