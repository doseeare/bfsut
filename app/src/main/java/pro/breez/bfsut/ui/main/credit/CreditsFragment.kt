package pro.breez.bfsut.ui.main.credit

import android.os.Bundle
import android.view.View
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditsBinding

@AndroidEntryPoint
class CreditsFragment : BaseFragment<FragmentCreditsBinding, CreditsViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val pagerAdapter = FragmentPagerItemAdapter(
            childFragmentManager, createTabs()
        )
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setViewPager(binding.viewPager)
    }

    private fun createTabs(): FragmentPagerItems {
        val creator = FragmentPagerItems.with(requireContext())
        creator.add("Кредиты", CreditStatusTabFragment::class.java)
        creator.add("Выдано ", CreditIssuedTabFragment::class.java)
        return creator.create()
    }
}