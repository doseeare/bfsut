package pro.breez.bfsut.ui.main.active_logs.calculate

import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCalculateActiveLogsBinding

@AndroidEntryPoint
class CalculateActiveLogsFragment :
    BaseFragment<FragmentCalculateActiveLogsBinding, CalculateActiveLogViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentLogLV.observe(viewLifecycleOwner) {
            binding.nameTv.text = "Сбор молока у ${it.farmer_name}"
            binding.dayEt.setText("${it.morning} л")
            binding.eveningEt.setText("${it.morning} л")
            binding.milkPriceTv.text = "${it.milk_price} сом\nза литр"
            binding.totalAmountTv.text = "Итого: ${it.overall} сом"
            binding.calculateBtn.setOnClickListener { _ ->
                viewModel.calculateLog(it.id)
            }
        }
    }

}