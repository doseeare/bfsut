package pro.breez.bfsut.ui.main.active_logs.calculate

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCalculateActiveLogsBinding
import pro.breez.bfsut.ui.main.active_logs.ActiveLogViewModel

@AndroidEntryPoint
class CalculateActiveLogsFragment :
    BaseFragment<FragmentCalculateActiveLogsBinding, CalculateActiveLogViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {

        }
    }

}