package pro.breez.bfsut.ui.main.filter

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentFilterBinding
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding, FilterViewModel>() {

    private var resetGroupClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewModel.farmerLV.observe(viewLifecycleOwner) {
            binding.farmerEt.text = it.full_name
        }
        viewModel.rangeDateLv.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.fromEt.editText.setText(it.start)
                binding.toEt.editText.setText(it.end)
                resetFilterGroup()
            } else {
                binding.fromEt.text = ""
                binding.toEt.text = ""
            }
        }
    }

    private fun initViews() = with(binding) {
        backBtn.setOnClickOnceListener {
            viewModel.backBtnClicked()
        }
        farmerEt.setOnClickListener {
            viewModel.farmerClicked()
        }

        filterSpanGroup.setOnSelectListener { selectedButton ->
            viewModel.spanSelected(selectedButton.id, resetGroupClicked)
            resetGroupClicked = false
        }

        fromEt.setOnClickListener {
            viewModel.showDatePicker(true)
        }

        toEt.setOnClickListener {
            viewModel.showDatePicker(false)
        }

        acceptBtn.setOnClickOnceListener {
            viewModel.acceptClicked()
        }
        filterSpanGroup.selectButton(binding.allTimeSpan)
    }

    private fun resetFilterGroup() {
        resetGroupClicked = true
        binding.filterSpanGroup.selectButton(binding.noneSpan)
    }

}