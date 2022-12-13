package pro.breez.bfsut.ui.main.filter_result.issued

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentIssuedFilterResultBinding
import pro.breez.bfsut.ui.main.credit_issued.CreditIssuedFragment

class IssuedFilterResultFragment :
    BaseFragment<FragmentIssuedFilterResultBinding, IssuedFilterResultViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
        viewModel.titleLV.observe(viewLifecycleOwner) {
            binding.resultTitleTv.text = it
        }
        binding.toolbar.setTitle("Результаты по:")
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
            Triple("Плановые", CreditIssuedFragment::class.java, viewModel.filterResult),
            Triple("Просроченные", CreditIssuedFragment::class.java, viewModel.filterResult),
            Triple("Все", CreditIssuedFragment::class.java, viewModel.filterResult)
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