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
import pro.breez.domain.model.output.FarmersModel

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
    }

    fun refreshInfo() {
        viewModel.refreshInfo()
    }

    private fun initObservers() {
        viewModel.farmersLV.observe(viewLifecycleOwner) { farmers ->
            val adapter =
                FarmersAdapter(
                    itemList = farmers.take(farmers.size / 2) as ArrayList<FarmersModel>,

                    addClicked = {
                        viewModel.addMilkToFarmer(it)
                    },
                    itemClicked = {
                        viewModel.farmerClicked(it)
                    })

            binding.showMoreBtn.setOnClickOnceListener {
                if (showMore) {
                    binding.showMoreBtn.text = "Скрыть"
                    adapter.update(farmers.take(farmers.size) as ArrayList<FarmersModel>)
                } else {
                    binding.showMoreBtn.text = "Показать еще"
                    adapter.update(farmers.take(farmers.size / 2) as ArrayList<FarmersModel>)
                }
                showMore = !showMore
            }
            binding.farmersRv.adapter = adapter
        }

        viewModel.milkPriceLV.observe(viewLifecycleOwner) {
            binding.priceOfLiterBtn.text = "$it сом за литр молока"
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
}