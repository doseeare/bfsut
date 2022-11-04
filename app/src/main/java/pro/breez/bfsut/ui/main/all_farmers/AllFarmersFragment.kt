package pro.breez.bfsut.ui.main.all_farmers

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.AllFarmerAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentAllFarmersBinding


@AndroidEntryPoint
class AllFarmersFragment : BaseFragment<FragmentAllFarmersBinding, AllFarmersViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        viewModel.farmers.observe(viewLifecycleOwner) {
            allFarmersRv.adapter = AllFarmerAdapter(it)
        }
        toolbar.setOnBackClickListener {
            viewModel.backClicked()
        }
    }
}