package pro.breez.bfsut.ui.main.add_milk

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.custom.mask.addDigitMask
import pro.breez.bfsut.databinding.FragmentAddMilkBinding
import pro.breez.bfsut.util.ifTrue
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class MilkAddFragment : BaseFragment<FragmentAddMilkBinding, MilkAddViewModel>() {

    private var isNameNotEmpty = false
    private var isMorningNotEmpty = false
    private var isEveningNotEmpty = false
    private var morningLiters: String? = null
    private var eveningLiters: String? = null

    private var defaultText = "0л"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        initValidate()
        binding.morningEt.isEnabled = true
    }

    private fun validateFields(){
        validateCreateBtn()
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

    private fun initValidate() {
        var morningTitleColor: Int
        binding.morningEt.doOnTextChanged { text, _, _, _ ->
            isMorningNotEmpty = !text.isNullOrBlank()
            morningTitleColor = if (isMorningNotEmpty) R.color.gray_text
            else R.color.text_bold_color

            binding.morningTitleTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    morningTitleColor
                )
            )
            validateFields()
        }

        var eveningTitleColor: Int
        binding.eveningEt.doOnTextChanged { text, _, _, _ ->
            isEveningNotEmpty = !text.isNullOrBlank()
            eveningTitleColor = if (isEveningNotEmpty) R.color.gray_text
            else R.color.text_bold_color

            binding.eveningTitleTv.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    eveningTitleColor
                )
            )
            validateFields()
        }
        binding.nameEt.editText.doOnTextChanged { text, _, _, _ ->
            isNameNotEmpty = !text.isNullOrBlank()
            validateFields()
        }
    }

    private fun initObservers() {
        viewModel.milkPriceLV.observe(viewLifecycleOwner) {
            binding.milkPriceTv.text = "${it.price} сом\nза литр"
        }
        viewModel.farmerLV.observe(viewLifecycleOwner) {
            binding.nameEt.text = it.full_name
            morningLiters = "${it.morning}л"
            eveningLiters = "${it.evening}л"

            if (morningLiters != defaultText)
                binding.morningEt.setText(morningLiters)
            if (eveningLiters != defaultText)
                binding.eveningEt.setText(eveningLiters)
            binding.morningEt.isEnabled = true

            viewModel.isSelectedFarmer.ifTrue {
                binding.nameEt.visibility = View.GONE
                binding.title.text = "Сбор молока\nу ${it.full_name}"
            }
        }
        viewModel.eveningStatusLV.observe(viewLifecycleOwner) {
            binding.eveningEt.isEnabled = it
        }
    }

    private fun validateCreateBtn() {
        val morningText = binding.morningEt.text.toString()
        val eveningText = binding.eveningEt.text.toString()

        val enabled = (isMorningNotEmpty || isEveningNotEmpty && isNameNotEmpty)
                && (morningLiters != morningText || eveningLiters != eveningText)
                && (morningText != defaultText || eveningText != defaultText)
        binding.createBtn.isEnabled = enabled
    }

    private fun initViews() = with(binding) {
        toolbar.setTitle("Сбор молока")
        toolbar.setOnBackClickListener(requireActivity()::onBackPressed)
        binding.morningEt.addDigitMask("л")
        binding.eveningEt.addDigitMask("л")
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