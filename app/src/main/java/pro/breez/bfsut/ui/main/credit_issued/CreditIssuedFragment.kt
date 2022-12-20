package pro.breez.bfsut.ui.main.credit_issued

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.CreditIssuedAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditIssuedBinding
import pro.breez.bfsut.util.visibility

@AndroidEntryPoint
class CreditIssuedFragment : BaseFragment<FragmentCreditIssuedBinding, CreditIssuedViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initArgs(requireArguments().getSerializable(KEY))
        initViews()
    }

    private fun initViews() {
        viewModel.getCreditIssued()
        viewModel.creditIssuedLV.observe(viewLifecycleOwner) {
            binding.creditCountTv.text = "${it.total_credit} кредитов"
            binding.overallTv.text = "${it.total_sum} c"
            binding.rv.adapter = CreditIssuedAdapter(it.data) {
                viewModel.itemCLicked(it)
            }
        }

        viewModel.notFoundLV.observe(viewLifecycleOwner) {
            binding.notFoundView.visibility(it)
            binding.rv.visibility(!it)
        }
    }

    companion object {
        const val KEY = "CreditIssuedFragment"
    }

}