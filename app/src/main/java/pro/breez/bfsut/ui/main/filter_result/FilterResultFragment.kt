package pro.breez.bfsut.ui.main.filter_result

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentFilterResultBinding
import pro.breez.bfsut.ui.main.active_logs.ActiveLogFragment
import pro.breez.bfsut.ui.main.all_logs.AllLogFragment
import pro.breez.bfsut.ui.main.paid_logs.PaidLogsFragment
import pro.breez.bfsut.util.alert.OnPageSelectedListener


@AndroidEntryPoint
class FilterResultFragment : BaseFragment<FragmentFilterResultBinding, FilterResultViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val strBuilder = StringBuilder()
        viewModel.filterResult.let {
            it.farmerName?.let {
                strBuilder.append(it)
            }
            it.range?.from?.let {
                strBuilder.append("\n")
                strBuilder.append("$it - ")
            }
            it.range?.to?.let {
                strBuilder.append("\n")
                strBuilder.append(it)
            }
            it.filterSpan?.title?.let {
                strBuilder.append("\n")
                strBuilder.append(it)
            }
        }
        binding.toolbar.setTitle("Результаты по:")
        binding.resultTitleTv.text = strBuilder.toString()
        binding.toolbar.setOnBackClickListener {
            popBackStack(R.id.navigation_log)
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
                if (fragment != null) {
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