package pro.breez.bfsut.ui.main.all_farmers

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentAllFarmersBinding
import pro.breez.bfsut.adapter.AllFarmerAdapter


@AndroidEntryPoint
class AllFarmersFragment : BaseFragment<FragmentAllFarmersBinding, AllFarmersViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        allFarmersRv.adapter = AllFarmerAdapter(viewModel.farmers)
        toolbar.setOnBackClickListener {
            viewModel.backClicked()
        }
    }
}