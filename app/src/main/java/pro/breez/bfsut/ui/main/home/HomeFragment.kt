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
import pro.breez.domain.model.output.FarmerCheckModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private var showMore = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.logoutBtn.setOnClickOnceListener {
            viewModel.logOut {
                startActivity(Intent(context, AuthActivity::class.java))
                requireActivity().finish()
            }
        }

        binding.showAllBtn.setOnClickOnceListener {
            viewModel.showAll()
        }
        binding.priceOfLiterBtn.setOnClickOnceListener {
            viewModel.navigateToChangePrice()
        }
        binding.nameTv.text = viewModel.dataPreference.userName
    }

    fun refreshInfo() {
        viewModel.refreshInfo()
    }

    private fun initObservers() {
        val adapter =
            FarmersAdapter(
                itemList = arrayListOf(),
                addClicked = {
                    viewModel.addMilkToFarmer(it)
                },
                itemClicked = {
                    viewModel.farmerClicked(it)
                })
        viewModel.farmersCheckLV.observe(viewLifecycleOwner) { farmers ->
            binding.showMoreBtn.setOnClickOnceListener {
                showMore = !showMore
                showOrHideFarmers(farmers, adapter)
            }
            binding.farmersRv.adapter = adapter
            showOrHideFarmers(farmers, adapter)
        }
        viewModel.milkPriceLV.observe(viewLifecycleOwner) {
            binding.priceOfLiterBtn.text = "$it сом за литр молока"
        }
        viewModel.userNameLV.observe(viewLifecycleOwner) {
            binding.nameTv.text = it
        }
        viewModel.totalMilkLv.observe(viewLifecycleOwner) {
            binding.morningPanel.apply {
                setLiters(it.morning_milk_sum)
                setSum(it.morning_price_sum)
            }
            binding.eveningPanel.apply {
                setLiters(it.evening_milk_sum)
                setSum(it.evening_price_sum)
            }
        }
    }

    private fun showOrHideFarmers(
        farmers: ArrayList<FarmerCheckModel>,
        adapter: FarmersAdapter,
    ) {
        if (farmers.size > 5) {
            if (showMore) {
                binding.showMoreBtn.text = "Показать еще"
                adapter.update(farmers.take(farmers.size / 2) as ArrayList)
            } else {
                binding.showMoreBtn.text = "Скрыть"
                adapter.update(farmers.take(farmers.size) as ArrayList)
            }
        } else {
            adapter.update(farmers)
            binding.showMoreBtn.isEnabled = false
        }
    }

    fun creditAdd() {
        viewModel.navigateToCreditAdd()
    }
}