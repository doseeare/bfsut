package pro.breez.bfsut.ui.main.credit

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentNotificationsBinding
import pro.breez.bfsut.ui.main.credit.adapter.CreditsAdapter

@AndroidEntryPoint
class CreditsFragment : BaseFragment<FragmentNotificationsBinding, CreditsViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewModel.creditsLV.observe(viewLifecycleOwner) {
            val adapter = CreditsAdapter(it as ArrayList)
            binding.creditRv.adapter = adapter
        }
    }

}