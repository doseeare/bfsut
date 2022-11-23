package pro.breez.bfsut.ui.issued_graph

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentIssuedGraphBinding

@AndroidEntryPoint
class IssuedGraphFragment : BaseFragment<FragmentIssuedGraphBinding, IssuedGraphViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.creditId = requireArguments().getString(KEY, "")
        viewModel.getIssuedGraph()
        viewModel.graphLV.observe(viewLifecycleOwner) {
            binding.pdfView.fromStream(it).load()
        }
    }


    companion object {
        const val KEY = "IssuedGraphFragment"
    }

}