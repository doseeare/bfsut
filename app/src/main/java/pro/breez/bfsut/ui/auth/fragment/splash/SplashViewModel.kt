package pro.breez.bfsut.ui.auth.fragment.splash

import android.os.CountDownTimer
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.navigation.ActivityTransaction
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.data.cache.DataPreference
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataPreference: DataPreference
) : BaseViewModel() {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        countAndNavigate()
    }

    private fun countAndNavigate() {
        val timer = object : CountDownTimer(3_000, 1_000) {
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (dataPreference.token.isNotEmpty()) {
                    navigateToMain()
                } else {
                    navigateToFragment.startEvent(FragmentTransaction(navigationId = R.id.splash_to_auth))
                }
            }
        }
        timer.start()
    }

    private fun navigateToMain() {
        navigateToActivity.startEvent(ActivityTransaction(R.id.splash_fragment_to_main_activity))
    }
}