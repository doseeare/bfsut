package pro.breez.bfsut.ui.main.active_logs

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCalculateActiveLogsBinding

@AndroidEntryPoint
class CalculateActiveLogsFragment :
    BaseFragment<FragmentCalculateActiveLogsBinding, ActiveLogViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}