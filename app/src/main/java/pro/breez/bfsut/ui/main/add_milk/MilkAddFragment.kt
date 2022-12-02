package pro.breez.bfsut.ui.main.add_milk

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.custom.view.CustomDropDownEditText
import pro.breez.bfsut.databinding.FragmentAddMilkBinding
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class MilkAddFragment : BaseFragment<FragmentAddMilkBinding, MilkAddViewModel>() {

    private var isNameNotEmpty = false
    private var isMorningNotEmpty = false
    private var isEveningNotEmpty = false

    private lateinit var fieldsValidate: () -> Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initValidate()
        initObservers()
    }

    private fun initValidate() {
        fieldsValidate = {
            binding.createBtn.isEnabled = (isMorningNotEmpty || isEveningNotEmpty) && isNameNotEmpty
            var totalSum = 0L
            viewModel.farmerLV.value?.let {
                val morning = binding.morningEt.text.toString().filter { it.isDigit() }
                if (morning.isNotBlank()) {
                    totalSum += morning.toInt() * it.morning_price
                }
                val evening = binding.eveningEt.text.toString()
                    .filter { it.isDigit() }
                if (evening.isNotBlank()) {
                    totalSum += evening.toInt() * it.evening_price
                }
                binding.totalSumTv.text = "Итого: $totalSum сом"
            }
        }
        binding.morningEt.doOnTextChanged { text, _, _, _ ->
            val stringText = text.toString()
            val isNotEmpty = stringText.isNotEmpty() || stringText.isNotBlank()
            isMorningNotEmpty = isNotEmpty
            fieldsValidate.invoke()
        }
        binding.eveningEt.doOnTextChanged { text, _, _, _ ->
            fieldsValidate.invoke()
        }
        binding.nameEt.editText.doOnTextChanged { text, _, _, _ ->
            fieldsValidate.invoke()
        }
    }

    private fun initObservers() {
        viewModel.milkPriceLV.observe(viewLifecycleOwner) {
            binding.milkPriceTv.text = "${it.price} сом\nза литр"
        }
        viewModel.farmerLV.observe(viewLifecycleOwner) {
            binding.nameEt.text = it.full_name
            binding.morningEt.setText("${it.morning} л")
            binding.eveningEt.setText("${it.evening} л")
            binding.morningEt.isEnabled = true
        }
        viewModel.isSelectedFarmer.observe(viewLifecycleOwner) {
            if (it) binding.nameEt.setType(CustomDropDownEditText.NONE)
        }
        viewModel.eveningStatusLV.observe(viewLifecycleOwner) {
            binding.eveningEt.isEnabled = it
        }
    }

    private fun initViews() = with(binding) {
        toolbar.setTitle("Сбор молока")
        toolbar.setOnBackClickListener(requireActivity()::onBackPressed)
        createBtn.setOnClickOnceListener {
            viewModel.createBtnClicked(
                morningEt.text.toString(),
                eveningEt.text.toString()
            )
        }
        nameEt.setOnClickListener {
            viewModel.farmerClicked()
        }
    }
}