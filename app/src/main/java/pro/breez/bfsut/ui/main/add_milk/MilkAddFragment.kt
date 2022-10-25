package pro.breez.bfsut.ui.main.add_milk

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
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
            binding.createBtn.isEnabled = isNameNotEmpty && isEveningNotEmpty && isMorningNotEmpty
            var totalSum = 0L
            val literPrice = viewModel.milkPriceLV.value!!.price
            if (isMorningNotEmpty && isEveningNotEmpty) {
                val evening = binding.eveningEt.text.toString().toInt()
                val morning = binding.morningEt.text.toString().toInt()
                totalSum = ((evening * literPrice) + (morning * literPrice)).toLong()
            }
            binding.totalSumTv.text = "Итого: $totalSum сом"
        }
        binding.morningEt.doOnTextChanged { text, start, before, count ->
            val stringText = text.toString()
            val isNotEmpty = stringText.isNotEmpty() || stringText.isNotBlank()
            isMorningNotEmpty = isNotEmpty
            fieldsValidate.invoke()
        }
        binding.eveningEt.doOnTextChanged { text, start, before, count ->
            val stringText = text.toString()
            val isNotEmpty = stringText.isNotEmpty() || stringText.isNotBlank()
            isEveningNotEmpty = isNotEmpty
            fieldsValidate.invoke()
        }
        binding.nameEt.editText.doOnTextChanged { text, start, before, count ->
            val stringText = text.toString()
            val isNotEmpty = stringText.isNotEmpty() || stringText.isNotBlank()
            isNameNotEmpty = isNotEmpty
            fieldsValidate.invoke()
        }
    }

    private fun initObservers() {
        viewModel.milkPriceLV.observe(viewLifecycleOwner) {
            binding.milkPriceTv.text = "${it.price} сом\nза литр"
        }
        viewModel.farmerLV.observe(viewLifecycleOwner) {
            binding.nameEt.text = it.full_name
        }
    }

    private fun initViews() = with(binding) {
        toolbar.setTitle("Сбор молока")
        toolbar.setOnBackClickListener(requireActivity()::onBackPressed)
        createBtn.setOnClickOnceListener {
            viewModel.createBtnClicked(morningEt.text.toString(), eveningEt.text.toString())
        }
        nameEt.setOnClickListener {
            viewModel.farmerClicked()
        }
    }
}