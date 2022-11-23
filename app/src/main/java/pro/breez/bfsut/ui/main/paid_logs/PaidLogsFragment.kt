package pro.breez.bfsut.ui.main.paid_logs

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.PaidLogsAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentPaidLogBinding
import pro.breez.bfsut.model.FilterResult
import pro.breez.bfsut.ui.main.filter_result.log.LogFilterResultFragment
import pro.breez.bfsut.util.alert.OnPageSelectedListener
import pro.breez.domain.model.output.PaidLogModel


@AndroidEntryPoint
class PaidLogsFragment : BaseFragment<FragmentPaidLogBinding, PaidLogsViewModel>(),
    OnPageSelectedListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initArgs()
    }

    private fun initArgs() {
        val arg = requireArguments().getSerializable(LogFilterResultFragment.BUNDLE_KEY)
        if (arg != null) {
            viewModel.filterResult = arg as FilterResult
        }
    }

    private fun initViews() {
        val adapter = PaidLogsAdapter(arrayListOf()) {
            viewModel.itemClicked(it)
        }
        binding.paidLogRv.adapter = adapter
        viewModel.paidLogsLV.observe(viewLifecycleOwner) {
            checkList(it)
            adapter.update(it)
        }
    }

    private fun checkList(list: ArrayList<PaidLogModel>) {
        if (list.isEmpty()) {
            binding.notFoundView.visibility = View.VISIBLE
            binding.paidLogRv.visibility = View.GONE
        } else {
            binding.notFoundView.visibility = View.GONE
            binding.paidLogRv.visibility = View.VISIBLE
        }
    }

    override fun onPageSelected() {
        viewModel.getPaidLogs()
    }
}