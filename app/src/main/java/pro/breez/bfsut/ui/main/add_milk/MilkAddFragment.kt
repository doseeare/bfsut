package pro.breez.bfsut.ui.main.add_milk

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentAddMilkBinding
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class MilkAddFragment : BaseFragment<FragmentAddMilkBinding, MilkAddViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewModel.milkPriceLV.observe(viewLifecycleOwner) {
            binding.milkPriceTv.text = "${it.price} сом\nза литр"
        }
    }

    private fun initViews() = with(binding) {
        toolbar.setTitle("Сбор молока")
        toolbar.setOnBackClickListener(requireActivity()::onBackPressed)
        createBtn.setOnClickOnceListener {
            viewModel.createBtnClicked()
        }
    }
}