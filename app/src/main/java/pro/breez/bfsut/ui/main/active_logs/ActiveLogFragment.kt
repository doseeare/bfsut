package pro.breez.bfsut.ui.main.active_logs

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.adapter.ActiveLogAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentActiveLogBinding

@AndroidEntryPoint
class ActiveLogFragment : BaseFragment<FragmentActiveLogBinding, ActiveLogViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewModel.activeLogsLV.observe(viewLifecycleOwner) {
            val adapter = ActiveLogAdapter(it,
                itemClicked = { item ->
                    viewModel.itemClicked()
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
            binding.activeLogRv.adapter = adapter
        }

        viewModel.selectedLogsPriceInfoLV.observe(viewLifecycleOwner) {
            if (it.first == 0 && it.second == 0) {
                binding.bottomInfoBar.setBackgroundResource(R.drawable.bg_bottom_info_bar_disabled)
                binding.infoTitleTv.text = getString(R.string.choose_logs)
                binding.infoLiterTv.visibility = View.GONE
                binding.infoPriceTv.visibility = View.GONE
            }else{
                binding.bottomInfoBar.setBackgroundResource(R.drawable.bg_bottom_info_bar)
                binding.infoTitleTv.text = getString(R.string.make_calculate)
                binding.infoLiterTv.visibility = View.VISIBLE
                binding.infoPriceTv.visibility = View.VISIBLE
                binding.infoLiterTv.text = "${it.first}л"
                binding.infoPriceTv.text = "${it.second} сом"
            }
        }
    }

}