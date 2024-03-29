package pro.breez.bfsut.ui.main.log

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentLogBinding
import pro.breez.bfsut.ui.host.MainActivity
import pro.breez.bfsut.ui.main.active_logs.ActiveLogFragment
import pro.breez.bfsut.ui.main.all_logs.AllLogFragment
import pro.breez.bfsut.ui.main.paid_logs.PaidLogsFragment
import pro.breez.bfsut.util.alert.OnPageSelectedListener
import pro.breez.bfsut.util.ifNotNull
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class LogFragment : BaseFragment<FragmentLogBinding, LogViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabLayout()
        initViews()
    }

    private fun initViews() {
        binding.filterBtn.setOnClickOnceListener {
            viewModel.filterClicked()
        }
    }

    private fun setUpTabLayout() {
        val pagerAdapter = FragmentPagerItemAdapter(
            childFragmentManager, createTabs()
        )
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = pagerAdapter.count
        binding.tabLayout.setViewPager(binding.viewPager)
        binding.tabLayout.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            val parentActivity = (requireActivity() as MainActivity)
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                pagerAdapter.getPage(position).ifNotNull {
                    (it as OnPageSelectedListener).onPageSelected()
                }
                parentActivity.showDivider(position != 0)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    private fun createTabs(): FragmentPagerItems {
        val creator = FragmentPagerItems.with(requireContext())
        creator.add("Активные", ActiveLogFragment::class.java)
        creator.add("Рассчитано", PaidLogsFragment::class.java)
        creator.add("Все", AllLogFragment::class.java)
        return creator.create()
    }

    fun creditAdd() {
        viewModel.navigateToCreditAdd()
    }
}