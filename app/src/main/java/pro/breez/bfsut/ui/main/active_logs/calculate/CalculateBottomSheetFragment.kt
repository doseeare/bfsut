package pro.breez.bfsut.ui.main.active_logs.calculate

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseBottomSheetFragment
import pro.breez.bfsut.databinding.FragmentCalculateBottomSheetBinding
import pro.breez.bfsut.ui.main.active_logs.ActiveLogFragment
import pro.breez.bfsut.ui.main.all_logs.AllLogFragment
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.domain.model.output.LogsModel


@AndroidEntryPoint
class CalculateBottomSheetFragment :
    BaseBottomSheetFragment<FragmentCalculateBottomSheetBinding, CalculateLogViewModel>() {

    val dismissLiveData = MutableLiveData<Unit>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments().let {
            viewModel.log = it.getSerializable(LOG_KEY) as? LogsModel
        }
        initViews()
    }

    private fun initViews() = with(binding) {
        viewModel.log?.let { log ->
            nameTv.text = log.farmer_name
            postDateTv.text = log.date
            dayLitersTv.text = "${log.morning}л"
            eveningLitersTv.text = "${log.evening}л"
            totalSumTv.text = "${log.overall}с"
            milkPriceTv.text = "${log.milk_price} сом\n за литр"

            var statusTitle = ""
            var statusBg = 0

            when (log.status) {
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
                    calcBtn.visibility = View.GONE
                    calcDateTv.visibility = View.VISIBLE
                    calcDateTv.text = "Рассчитано ${log.paid_date}"
                }
            }
            calcBtn.setOnClickOnceListener {
                viewModel.calculateLog(log.id) {
                    dismiss()
                    when (val parentFragment = parentFragment) {
                        is AllLogFragment -> {
                            parentFragment.onPageSelected()
                        }
                        is ActiveLogFragment -> {
                            parentFragment.onPageSelected()
                        }
                    }
                }
            }
            statusTv.text = statusTitle
            statusContainer.setBackgroundResource(statusBg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissLiveData.postValue(Unit)
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