package pro.breez.bfsut.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.adapter.FarmersAdapter
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentHomeBinding
import pro.breez.bfsut.ui.auth.activity.AuthActivity
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private var showMore = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val adapter = FarmersAdapter(viewModel.farmers.take(8) as ArrayList)
        binding.showMoreBtn.setOnClickOnceListener {
            if (showMore) {
                binding.showMoreBtn.text = "Скрыть"
                adapter.update(viewModel.farmers.take(12) as ArrayList)
            } else {
                binding.showMoreBtn.text = "Показать еще"
                adapter.update(viewModel.farmers.take(8) as ArrayList)
            }
            showMore = !showMore
        }
        binding.logoutBtn.setOnClickOnceListener {
            viewModel.logOut {
                startActivity(Intent(context, AuthActivity::class.java))
                requireActivity().finish()
            }
        }
        binding.farmersRv.adapter = adapter

        binding.showAllBtn.setOnClickOnceListener {
            viewModel.showAll()
        }
    }

}