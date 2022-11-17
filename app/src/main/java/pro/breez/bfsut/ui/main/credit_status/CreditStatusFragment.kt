package pro.breez.bfsut.ui.main.credit_status

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.CreditsAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditStatusBinding
import pro.breez.bfsut.model.CreditStatusEnum

@AndroidEntryPoint
class CreditStatusFragment : BaseFragment<FragmentCreditStatusBinding, CreditStatusViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.creditStatus = requireArguments().getSerializable(STATUS_KEY) as CreditStatusEnum
        initViews()
    }

    private fun initViews() {
        viewModel.getCredits()
        viewModel.creditsLV.observe(viewLifecycleOwner) {
            val isAllCredits = viewModel.creditStatus == CreditStatusEnum.ALL
            binding.rv.adapter = CreditsAdapter(it, isAllCredits){
                viewModel.creditItemClicked(it)
            }
        }
    }

    companion object {
        const val STATUS_KEY = "credit_status_key"
    }

}