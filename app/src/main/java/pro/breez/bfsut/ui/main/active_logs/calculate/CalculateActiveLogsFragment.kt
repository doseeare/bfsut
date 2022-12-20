package pro.breez.bfsut.ui.main.active_logs.calculate

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.custom.mask.addDigitMask
import pro.breez.bfsut.databinding.FragmentCalculateActiveLogsBinding
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class CalculateActiveLogsFragment :
    BaseFragment<FragmentCalculateActiveLogsBinding, CalculateActiveLogViewModel>() {

    private lateinit var fieldsValidate: () -> Unit
    private var isMorningNotEmpty = false
    private var isEveningNotEmpty = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        binding.toolbarBackBtn.setOnClickOnceListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initViews() {
        binding.dayEt.addDigitMask("л")
        binding.eveningEt.addDigitMask("л")
        viewModel.currentLogLV.observe(viewLifecycleOwner) {
            binding.nameTv.text = "Сбор молока у ${it.farmer_name}"
            binding.dayEt.setText("${it.morning}л")
            binding.eveningEt.setText("${it.evening}л")
            binding.milkPriceTvEvening.text = "цена за вечер: ${it.evening_price} сом"
            binding.milkPriceTvMorning.text = "цена за утро: ${it.morning_price} сом"
            binding.calculateBtn.setOnClickOnceListener { _ ->
                viewModel.calculateLog(it.id)
            }
            binding.saveChangesBtn.setOnClickOnceListener { _ ->
                val evening = binding.eveningEt.text.toString()
                val morning = binding.dayEt.text.toString()
                viewModel.saveChanges(it, morning, evening)
            }
        }

        fieldsValidate = {
            binding.calculateBtn.isEnabled =
                (isMorningNotEmpty || isEveningNotEmpty)
            binding.saveChangesBtn.isEnabled =
                (binding.dayEt.text.toString() != "${viewModel.currentLogLV.value?.morning}л") ||
                        (binding.eveningEt.text.toString() != "${viewModel.currentLogLV.value?.evening}л")
            var totalSum = 0L
            viewModel.currentLogLV.value?.let {
                val morning = binding.dayEt.text.toString().filter { it.isDigit() }
                if (morning.isNotBlank())
                    totalSum += morning.toInt() * it.morning_price
                val evening = binding.eveningEt.text.toString()
                    .filter { it.isDigit() }
                if (evening.isNotBlank())
                    totalSum += evening.toInt() * it.evening_price
                binding.totalAmountTv.text = "Итого: $totalSum сом"
            }
        }

        binding.dayEt.doOnTextChanged { text, _, _, _ ->
            isMorningNotEmpty = !text.isNullOrBlank()
            fieldsValidate.invoke()
        }
        binding.eveningEt.doOnTextChanged { text, _, _, _ ->
            isEveningNotEmpty = !text.isNullOrBlank()
            fieldsValidate.invoke()
        }
        binding.deleteBtn.setOnClickOnceListener {
            viewModel.delete()
        }

    }
}

