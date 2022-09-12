package pro.breez.bfsut.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import pro.breez.bfsut.R
import pro.breez.bfsut.exception.NavigationInitializationException
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null

    protected val binding: VB get() = _binding!!

    protected val navigation: NavController by lazy { initializeNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    fun popBackStack(@IdRes navigationId: Int, inclusive: Boolean = false) {
        navigation.popBackStack(navigationId, inclusive)
    }

    fun navigateToFragment(fragmentTransaction: FragmentTransaction) {
        try {
            navigation.navigate(
                fragmentTransaction.navigationId,
                fragmentTransaction.bundle,
                fragmentTransaction.navOptions
            )
        } catch (i: IllegalArgumentException) {
        }
    }

    fun navigateToActivity(activityTransaction: ActivityTransaction) {
        navigation.navigate(activityTransaction.navigationId, activityTransaction.bundle)
        if (activityTransaction.replace) {
            finish()
        }
    }

    private fun initializeNavController(): NavController {
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            throw NavigationInitializationException("Initialization required to be called after activity's setContentView")
        }

        return Navigation.findNavController(this, R.id.nav_host)
    }
}