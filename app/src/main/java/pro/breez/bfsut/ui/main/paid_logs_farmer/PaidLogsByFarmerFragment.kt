package pro.breez.bfsut.ui.main.paid_logs_farmer

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.PaidLogsFarmerAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentPaidLogsFarmerBinding
import pro.breez.bfsut.model.FilterResult
import pro.breez.bfsut.ui.host.MainActivity
import pro.breez.bfsut.ui.main.filter_result.log.LogFilterResultFragment
import pro.breez.bfsut.util.alert.OnPageSelectedListener

@AndroidEntryPoint
class PaidLogsByFarmerFragment :
    BaseFragment<FragmentPaidLogsFarmerBinding, PaidLogsByFarmerViewModel>(),
    OnPageSelectedListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArgs()
        initObservers()
    }

    private fun initObservers() {
        val adapter = PaidLogsFarmerAdapter(arrayListOf())
        viewModel.paidLogs.observe(viewLifecycleOwner) {
            adapter.update(it as ArrayList)
            checkList(it)
        }
        binding.rv.adapter = adapter
    }

    private fun initArgs() {
        requireArguments().getSerializable(LogFilterResultFragment.BUNDLE_KEY)?.let {
            viewModel.filterResult = it as FilterResult
            viewModel.getPaidLogs()
        }
    }


    private fun checkList(list: List<*>) {
        if (list.isEmpty()) {
            binding.notFoundView.visibility = View.VISIBLE
            binding.rv.visibility = View.GONE
        } else {
            binding.notFoundView.visibility = View.GONE
            binding.rv.visibility = View.VISIBLE
        }
    }

    override fun onPageSelected() {
        (requireActivity() as MainActivity).showDivider(true)
    }

}