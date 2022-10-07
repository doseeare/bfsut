package pro.breez.bfsut.ui.main.active_logs.calculate

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseBottomSheetFragment
import pro.breez.bfsut.databinding.FragmentCalculateBottomSheetBinding
import pro.breez.domain.model.output.LogsModel


@AndroidEntryPoint
class CalculateBottomSheetFragment :
    BaseBottomSheetFragment<FragmentCalculateBottomSheetBinding, CalculateLogViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().let {
            viewModel.log = it.getSerializable(LOG_KEY) as? LogsModel
        }
        initViews()
    }

    private fun initViews() = with(binding) {
        viewModel.log?.let {
            nameTv.text = it.farmer_name
            postDateTv.text = it.date
            dayLitersTv.text = "${it.morning}л"
            eveningLitersTv.text = "${it.evening}л"
            totalSumTv.text = "${it.overall}с"
            milkPriceTv.text = "${it.milk_price} сом\n за литр"

            var statusTitle = ""
            var statusBg = 0

            when (it.status) {
                "active" -> {
                    statusTitle = "Активный"
                    statusBg = pro.breez.bfsut.R.drawable.bg_active_span
                    totalTv.visibility = View.VISIBLE
                    calcDateTv.visibility = View.INVISIBLE
                }
                "paid" -> {
                    statusTitle = "Рассчитан"
                    statusBg = pro.breez.bfsut.R.drawable.bg_paid_span
                    totalTv.visibility = View.INVISIBLE
                    calcDateTv.visibility = View.VISIBLE
                }
            }
            statusTv.text = statusTitle
            statusContainer.setBackgroundResource(statusBg)
        }
    }

    override fun getTheme(): Int {
        return pro.breez.bfsut.R.style.CustomBottomSheetDialog
    }

    companion object {
        const val LOG_KEY = "LOG_KEY"
        fun newInstance(log: LogsModel): CalculateBottomSheetFragment {
            val bundle = Bundle()
            val fragment = CalculateBottomSheetFragment()
            bundle.putSerializable(LOG_KEY, log)
            fragment.arguments = bundle
            return fragment
        }
    }

}