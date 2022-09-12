package pro.breez.bfsut.base

import android.content.Intent
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pro.breez.bfsut.helper.SingleLiveEvent
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.util.alert.LoadingViewParams
import pro.breez.bfsut.util.alert.snackbar.SnackbarNotificationBuilderInterface
import pro.breez.bfsut.util.permission.PermissionCheckerBuilder
import pro.breez.mobimarket.utility.alert.DialogBuilder

open class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    val setToast = SingleLiveEvent<Pair<String, Boolean>>()
    val showSnackBar = SingleLiveEvent<SnackbarNotificationBuilderInterface>()
    val showLoadingView = MutableLiveData<LoadingViewParams>()
    val dialogBuilderEvent = SingleLiveEvent<DialogBuilder>()
    val permissionCheckerEvent = SingleLiveEvent<PermissionCheckerBuilder>()
    val showBottomSheetFragment = SingleLiveEvent<BottomSheetDialogFragment>()
    val showDialogFragment = SingleLiveEvent<DialogFragment>()
    val navigateToFragment = SingleLiveEvent<FragmentTransaction>()
    val navigateToActivity = SingleLiveEvent<ActivityTransaction>()
    val handleIntent = SingleLiveEvent<Intent>()
    val popBackStack = SingleLiveEvent<Int>()
    val finish = SingleLiveEvent<Void>()
    val onNetworkConnectionLostAlert = SingleLiveEvent<Void>()
    val onNetworkConnectionRestored = SingleLiveEvent<Void>()
}