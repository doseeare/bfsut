package pro.breez.bfsut.ui.main.credit_search_result

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.CreditsAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditSearchResultBinding
import pro.breez.domain.model.output.CreditStatusModel
import pro.breez.domain.model.output.LogsModel

@AndroidEntryPoint
class CreditSearchResultFragment :
    BaseFragment<FragmentCreditSearchResultBinding, CreditSearchResultViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.nameTv.text = viewModel.farmer.full_name
        binding.toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
        val adapter = CreditsAdapter(arrayListOf(), true) {
            viewModel.creditItemClicked(it)
        }
        binding.rv.adapter = adapter
        viewModel.creditsLV.observe(viewLifecycleOwner) {
            adapter.update(it as ArrayList)
            checkList(it)
        }
    }

    private fun checkList(list: List<*>) {
        if (list.isEmpty()) {
            binding.notFoundView.visibility = View.VISIBLE
            binding.rv.visibility = View.GONE
        } else {
            binding.notFoundView.visibility = View.GONE
            binding.rv.visibility = View.VISIBLE
        }
    }

}