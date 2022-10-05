package pro.breez.bfsut.ui.main.paid_logs

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.PaidLogsAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentPaidLogBinding
import pro.breez.bfsut.util.alert.OnPageSelectedListener

@AndroidEntryPoint
class PaidLogsFragment : BaseFragment<FragmentPaidLogBinding, PaidLogsViewModel>(),
    OnPageSelectedListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
       val adapter = PaidLogsAdapter(arrayListOf()) {
            viewModel.itemClicked(it)
        }
        binding.paidLogRv.adapter = adapter
        viewModel.paidLogsLV.observe(viewLifecycleOwner) {
            adapter.update(it)
        }
    }

    override fun onPageSelected() {
        viewModel.getPaidLogs()
    }
}