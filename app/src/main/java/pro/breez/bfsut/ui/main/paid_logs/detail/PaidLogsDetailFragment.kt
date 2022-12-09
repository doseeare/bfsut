package pro.breez.bfsut.ui.main.paid_logs.detail

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.PaidLogDetailAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentPaidLogsDetailBinding
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PaidLogsDetailFragment :
    BaseFragment<FragmentPaidLogsDetailBinding, PaidLogsDetailViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val currentDate: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val dateTitle =
            if (currentDate == viewModel.log.paid_date) "За сегодня" else "Рассчитано за ${viewModel.log.paid_date}"
        binding.toolbar.setTitle(dateTitle)
        binding.totalSumTv.text = "${viewModel.log.total_sum} с"
        binding.totalMilkLitersTv.text = "${viewModel.log.total_milk} л"
        binding.toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
        viewModel.paidLogsDetailLV.observe(viewLifecycleOwner) {
            binding.paidLogRv.adapter = PaidLogDetailAdapter(it)
        }
    }
}