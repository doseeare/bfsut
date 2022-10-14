package pro.breez.bfsut.ui.main.all_logs

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.AllLogsAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentAllLogBinding
import pro.breez.bfsut.model.FilterResult
import pro.breez.bfsut.ui.main.filter_result.FilterResultFragment
import pro.breez.bfsut.util.alert.OnPageSelectedListener

@AndroidEntryPoint
class AllLogFragment : BaseFragment<FragmentAllLogBinding, AllLogViewModel>(),
    OnPageSelectedListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initArgs()
    }

    private fun initArgs() {
        val arg = requireArguments().getSerializable(FilterResultFragment.BUNDLE_KEY)
        if (arg != null) {
            viewModel.filterResult = arg as FilterResult
        }
    }

    private fun initViews() {
        val adapter = AllLogsAdapter(arrayListOf()) {
            viewModel.itemClick(it)
        }
        viewModel.allLogsLV.observe(viewLifecycleOwner) {
            binding.allLogsRv.adapter = adapter
            adapter.update(it)
        }
    }

    override fun onPageSelected() {
        viewModel.getAllLogs()
    }
}