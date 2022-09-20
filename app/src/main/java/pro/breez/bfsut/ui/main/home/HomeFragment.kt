package pro.breez.bfsut.ui.main.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentHomeBinding
import pro.breez.bfsut.ui.main.home.adapter.FarmersAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hello.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        initViews()
    }

    private fun initViews() {
        binding.farmersRv.adapter = FarmersAdapter(viewModel.farmers.take(8) as ArrayList)
    }

}