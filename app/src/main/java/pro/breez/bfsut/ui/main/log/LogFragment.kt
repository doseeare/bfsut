package pro.breez.bfsut.ui.main.log

import android.os.Bundle
import android.view.View
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentDashboardBinding

class LogFragment : BaseFragment<FragmentDashboardBinding, LogViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        hideOrShowBottomNavigation(false)
    }

}