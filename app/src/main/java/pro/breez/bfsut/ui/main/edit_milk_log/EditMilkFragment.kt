package pro.breez.bfsut.ui.main.edit_milk_log

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentEditMilkBinding
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class EditMilkFragment : BaseFragment<FragmentEditMilkBinding, EditMilkViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
        binding.toolbar.setTitle("Сбор молока")
        binding.nameEt.text = viewModel.farmer.full_name
        viewModel.foundLogLV.observe(viewLifecycleOwner) {
            binding.dayLitersTv.text = "${it.morning}л"
            binding.eveningLitersTv.text = "${it.evening}л"
            binding.totalSumTv.text = "Итого: ${it.overall} сом"
            binding.editBtn.isEnabled = true
        }
        binding.editBtn.setOnClickOnceListener {
            viewModel.editBtnClicked()
        }
    }
}