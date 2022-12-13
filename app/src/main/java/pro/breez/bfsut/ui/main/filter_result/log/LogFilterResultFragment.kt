package pro.breez.bfsut.ui.main.filter_result.log

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentLogFilterResultBinding
import pro.breez.bfsut.ui.main.active_logs.ActiveLogFragment
import pro.breez.bfsut.ui.main.all_logs.AllLogFragment
import pro.breez.bfsut.ui.main.paid_logs.PaidLogsFragment
import pro.breez.bfsut.util.alert.OnPageSelectedListener
import pro.breez.bfsut.util.ifNotNull

@AndroidEntryPoint
class LogFilterResultFragment :
    BaseFragment<FragmentLogFilterResultBinding, LogFilterResultViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.toolbar.setTitle("Результаты по:")

        viewModel.filterTitleLV.observe(viewLifecycleOwner){
            binding.resultTitleTv.text = it
        }
        binding.toolbar.setOnBackClickListener {
            when (findNavController().previousBackStackEntry?.destination?.id) {
                R.id.navigation_filter -> {
                    popBackStack(R.id.navigation_log)
                }
                R.id.navigation_farmer_profile -> {
                    requireActivity().onBackPressed()
                }
            }
        }

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
                val fragment =
                    pagerAdapter.getPage(position)
                fragment.ifNotNull {
                    (fragment as OnPageSelectedListener).onPageSelected()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun createTabs(): FragmentPagerItems {
        val bundle =
            Bundle().apply { putSerializable(BUNDLE_KEY, viewModel.filterResult) }
        val creator = FragmentPagerItems.with(requireContext())
        creator.add("Активные", ActiveLogFragment::class.java, bundle)
        creator.add("Рассчитано", PaidLogsFragment::class.java, bundle)
        creator.add("Все", AllLogFragment::class.java, bundle)
        return creator.create()
    }

    companion object {
        const val BUNDLE_KEY = "filter_result_key"
    }

}