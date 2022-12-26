package pro.breez.bfsut.ui.main.credit

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditsBinding
import pro.breez.bfsut.ui.host.MainActivity
import pro.breez.bfsut.util.setOnClickOnceListener

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
        binding.tabLayout.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            val parentActivity = (requireActivity() as MainActivity)
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.filterBtn.setImageResource(R.drawable.ic_credit_search)
                        viewModel.isCredit = true
                        parentActivity.showDivider(true)
                    }
                    1 -> {
                        binding.filterBtn.setImageResource(R.drawable.ic_credit_filter)
                        viewModel.isCredit = false
                        parentActivity.showDivider(false)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        binding.filterBtn.setOnClickOnceListener {
            viewModel.filterClicked()
        }
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as MainActivity).showDivider(true)
    }
    private fun createTabs(): FragmentPagerItems {
        val creator = FragmentPagerItems.with(requireContext())
        creator.add("Кредиты", CreditStatusTabFragment::class.java)
        creator.add("Выдано ", CreditIssuedTabFragment::class.java)
        return creator.create()
    }

    fun creditAdd() {
        viewModel.navigateToCreditAdd()
    }
}