package pro.breez.bfsut.ui.main.active_logs

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.adapter.ActiveLogAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentActiveLogBinding
import pro.breez.bfsut.model.FilterResult
import pro.breez.bfsut.ui.main.filter_result.log.LogFilterResultFragment
import pro.breez.bfsut.util.alert.OnPageSelectedListener
import pro.breez.bfsut.util.ifNotNull
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.domain.model.output.LogsModel

@AndroidEntryPoint
class ActiveLogFragment : BaseFragment<FragmentActiveLogBinding, ActiveLogViewModel>(),
    OnPageSelectedListener {

    private var logSelected = false

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
        viewModel.activeLogsLV.observe(viewLifecycleOwner) {
            val adapter = ActiveLogAdapter(it,
                itemClicked = { item ->
                    viewModel.itemClicked(item)
                },
                checkBoxChanged = { item, selected ->
                    viewModel.checkBoxChanged(item, selected)
                },
                eachCheckBoxChanged = { selected ->
                    binding.selectAllToggle.setChanges(selected)
                }
            )
            binding.selectAllToggle.setToggleCheckedListener { checked ->
                adapter.selectAll(checked)
                viewModel.selectAll(checked)
            }
            checkList(it)
            binding.activeLogRv.adapter = adapter
        }

        binding.bottomInfoBar.setOnClickOnceListener {
            if (logSelected)
                viewModel.calculateLogs()
        }

        viewModel.selectedLogsPriceInfoLV.observe(viewLifecycleOwner) {
            if (it.first == 0 && it.second == 0) {
                binding.bottomInfoBar.setBackgroundResource(R.drawable.bg_bottom_info_shape_disabled)
                binding.infoTitleTv.text = getString(R.string.choose_logs)
                binding.infoLiterTv.visibility = View.GONE
                binding.infoPriceTv.visibility = View.GONE
                logSelected = false
            } else {
                binding.bottomInfoBar.setBackgroundResource(R.drawable.bg_bottom_info_shape_enable)
                binding.infoTitleTv.text = getString(R.string.make_calculate)
                binding.infoLiterTv.visibility = View.VISIBLE
                binding.infoPriceTv.visibility = View.VISIBLE
                binding.infoLiterTv.text = "${it.first} л"
                binding.infoPriceTv.text = "${it.second} сом"
                logSelected = true
            }
        }
    }

    private fun checkList(list: List<LogsModel>) {
        if (list.isEmpty()) {
            binding.notFoundView.visibility = View.VISIBLE
            binding.activeLogRv.visibility = View.GONE
            binding.selectAllToggle.visibility = View.GONE
        } else {
            binding.notFoundView.visibility = View.GONE
            binding.activeLogRv.visibility = View.VISIBLE
            binding.selectAllToggle.visibility = View.VISIBLE
        }
    }

    override fun onPageSelected() {
        viewModel.getActiveLogs()
    }


}