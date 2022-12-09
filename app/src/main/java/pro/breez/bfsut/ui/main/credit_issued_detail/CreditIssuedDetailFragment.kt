package pro.breez.bfsut.ui.main.credit_issued_detail

import android.os.Bundle
import android.view.View
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditIssuedDetailBinding
import pro.breez.bfsut.ui.issued_graph.IssuedGraphFragment
import pro.breez.bfsut.ui.issued_info.IssuedInfoFragment

@AndroidEntryPoint
class CreditIssuedDetailFragment :
    BaseFragment<FragmentCreditIssuedDetailBinding, CreditIssuedDetailViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val pagerAdapter = FragmentPagerItemAdapter(childFragmentManager, createTabs())
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setViewPager(binding.viewPager)
        binding.toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun createTabs(): FragmentPagerItems {
        val creator = FragmentPagerItems.with(requireContext())
        creator.add(
            getString(R.string.information),
            IssuedInfoFragment::class.java,
            Bundle().apply { putString(IssuedInfoFragment.KEY, viewModel.creditId) })
        creator.add(
            getString(R.string.graphic),
            IssuedGraphFragment::class.java,
            Bundle().apply { putString(IssuedGraphFragment.KEY, viewModel.creditId) })
        return creator.create()
    }

}