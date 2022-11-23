package pro.breez.bfsut.ui.main.credit

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.databinding.FragmentCreditIssuedTabBinding
import pro.breez.bfsut.model.CreditIssueEnum
import pro.breez.bfsut.ui.main.credit_issued.CreditIssuedFragment
import javax.inject.Inject

@AndroidEntryPoint
class CreditIssuedTabFragment :
    BaseFragment<FragmentCreditIssuedTabBinding, CreditIssueTabViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val pagerAdapter = FragmentPagerItemAdapter(childFragmentManager, createTabs())

        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setViewPager(binding.viewPager)
        binding.tabLayout.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun createTabs(): FragmentPagerItems {
        val fragments = arrayOf(
            Triple("Плановые", CreditIssuedFragment::class.java, CreditIssueEnum.PLAN),
            Triple("Просроченные", CreditIssuedFragment::class.java, CreditIssueEnum.OVERDUE),
            Triple("Все", CreditIssuedFragment::class.java, CreditIssueEnum.ALL)
        )
        val creator = FragmentPagerItems.with(requireContext())
        for (fragment in fragments) {
            creator.add(
                fragment.first,
                fragment.second,
                Bundle().apply {
                    putSerializable(CreditIssuedFragment.KEY, fragment.third)
                }
            )
        }
        return creator.create()
    }
}

@HiltViewModel
class CreditIssueTabViewModel @Inject constructor() : BaseViewModel()