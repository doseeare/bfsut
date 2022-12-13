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
                binding.startEt.editText.setText(it.start)
                binding.endEt.editText.setText(it.end)
                resetFilterGroup()
            } else {
                binding.startEt.text = ""
                binding.endEt.text = ""
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
            acceptBtn.isEnabled = true
        }

        startEt.setOnClickListener {
            viewModel.showDatePicker(true)
        }

        endEt.setOnClickListener {
            if (startEt.text.isNotBlank())
                viewModel.showDatePicker(false)
        }

        acceptBtn.setOnClickOnceListener {
            viewModel.acceptClicked()
        }
        filterSpanGroup.selectButton(binding.allTimeSpan)
        acceptBtn.isEnabled = false
    }

    private fun resetFilterGroup() {
        resetGroupClicked = true
        binding.filterSpanGroup.selectButton(binding.noneSpan)
    }

}