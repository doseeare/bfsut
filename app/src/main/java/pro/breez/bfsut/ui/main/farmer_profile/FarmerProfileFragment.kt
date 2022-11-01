package pro.breez.bfsut.ui.main.farmer_profile

import android.os.Bundle
import android.view.View
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentFarmerProfileBinding

class FarmerProfileFragment : BaseFragment<FragmentFarmerProfileBinding, FarmerProfileViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() = with(binding) {

    }

    private fun initObservers()  = with(viewModel){

    }

}