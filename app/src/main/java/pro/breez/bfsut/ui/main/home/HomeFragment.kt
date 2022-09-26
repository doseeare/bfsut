package pro.breez.bfsut.ui.main.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentHomeBinding
import pro.breez.bfsut.ui.main.home.adapter.FarmersAdapter

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private var showMore = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hello.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        initViews()
    }

    private fun initViews() {
        val adapter = FarmersAdapter(viewModel.farmers.take(8) as ArrayList)
        binding.showMoreBtn.setOnClickListener {
            if (showMore) {
                binding.showMoreBtn.text = "Скрыть"
                adapter.update(viewModel.farmers.take(12) as ArrayList)
            } else {
                binding.showMoreBtn.text = "Показать еще"
                adapter.update(viewModel.farmers.take(8) as ArrayList)
            }
            showMore = !showMore
        }
        binding.farmersRv.adapter = adapter

        binding.showAllBtn.setOnClickListener {
            viewModel.showAll()
        }
    }

}