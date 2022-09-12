package pro.breez.bfsut.ui.auth.fragment.splash

import android.os.CountDownTimer
import androidx.lifecycle.LifecycleOwner
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction

class SplashViewModel : BaseViewModel() {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        countAndNavigate()
    }

    private fun countAndNavigate() {
        val timer = object : CountDownTimer(3_000, 1_000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                navigateToFragment.postValue(FragmentTransaction(navigationId = R.id.nav_auth))
            }
        }
        timer.start()
    }

}