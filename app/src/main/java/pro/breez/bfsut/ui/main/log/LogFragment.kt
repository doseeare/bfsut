package pro.breez.bfsut.ui.main.log

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentLogBinding
import pro.breez.bfsut.ui.main.log.pager.active.ActiveLogFragment
import pro.breez.bfsut.ui.main.log.pager.all.AllLogFragment
import pro.breez.bfsut.ui.main.log.pager.calculated.CalculatedLogFragment

class LogFragment : BaseFragment<FragmentLogBinding, LogViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabLayout()
    }

    private fun setUpTabLayout() {
        val pagerAdapter = FragmentPagerItemAdapter(
            childFragmentManager, createTabs()
        )
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
        val creator = FragmentPagerItems.with(requireContext())
        creator.add("Активные", ActiveLogFragment::class.java)
        creator.add("Рассчитано", CalculatedLogFragment::class.java)
        creator.add("Все", AllLogFragment::class.java)
        return creator.create()
    }


}