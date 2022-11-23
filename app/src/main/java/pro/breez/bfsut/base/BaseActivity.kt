package pro.breez.bfsut.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.ViewProgressBinding
import pro.breez.bfsut.exception.NavigationInitializationException
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.auth.activity.AuthActivity
import pro.breez.data.utility.adapter.TokenAuthenticator

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null

    protected val binding: VB get() = _binding!!

    protected val navigation: NavController by lazy { initializeNavController() }
    private var loadingView: ConstraintLayout? = null
    private var textViewLoadingMessage: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        setupLoadingView()
        setupBroadcast()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupBroadcast() {
        val logoutReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Toast.makeText(
                    this@BaseActivity,
                    "Пройдите авторизацию",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(context, AuthActivity::class.java))
                finish()
            }
        }
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(logoutReceiver, IntentFilter(TokenAuthenticator.LOG_OUT))
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    fun popBackStack(@IdRes id: Int? = null) {
        if (id == null)
            navigation.popBackStack()
        else
            navigation.popBackStack(id, false)
    }

    fun showLoading(@StringRes resourceId: Int) {
        loadingView?.let { loadingView ->
            loadingView.visibility = View.VISIBLE
            textViewLoadingMessage?.let {
                it.text = getString(resourceId)
            }
        }
    }

    fun hideLoading() {
        loadingView?.let {
            it.visibility = View.GONE
        }
    }

    private fun setupLoadingView() {
        val view = ViewProgressBinding.inflate(
            layoutInflater,
            window.decorView.rootView as ViewGroup?, true
        )
        loadingView = view.progressOverlay
        textViewLoadingMessage = view.textViewProgressMessage
    }

    fun navigateToFragment(fragmentTransaction: FragmentTransaction) {
        try {
            navigation.navigate(
                fragmentTransaction.navigationId,
                fragmentTransaction.bundle,
                fragmentTransaction.navOptions
            )
        } catch (i: IllegalArgumentException) {
            Log.e("BaseActivity", "${i.printStackTrace()}: ", )
        }
    }

    fun navigateToActivity(activityTransaction: ActivityTransaction) {
        navigation.navigate(activityTransaction.navigationId, activityTransaction.bundle)
        if (activityTransaction.replace) {
            finish()
        }
    }

    fun hideBottomNavigation() {
        val bottomNavigationBar = findViewById<CardView>(R.id.custom_nav) ?: return
        bottomNavigationBar.animate().translationY(bottomNavigationBar.height.toFloat())
            .setDuration(500).start()
        bottomNavigationBar.visibility = View.GONE
    }

    fun showBottomNavigation() {
        val bottomNavigationBar = findViewById<CardView>(R.id.custom_nav) ?: return
        bottomNavigationBar.animate().translationY(0f).setDuration(500).start()
        bottomNavigationBar.visibility = View.VISIBLE
    }

    private fun initializeNavController(): NavController {
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            throw NavigationInitializationException("Initialization required to be called after activity's setContentView")
        }

        return Navigation.findNavController(this, R.id.nav_host)
    }
}