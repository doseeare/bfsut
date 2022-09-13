package pro.breez.bfsut.ui.auth.fragment.splash

import android.os.CountDownTimer
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.FragmentTransaction
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        countAndNavigate()
    }

    private fun countAndNavigate() {
        val timer = object : CountDownTimer(3_000, 1_000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                navigateToFragment.startEvent(FragmentTransaction(navigationId = R.id.splash_to_auth))
            }
        }
        timer.start()
    }

}