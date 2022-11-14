package pro.breez.bfsut.ui.main.credit

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.databinding.FragmentCreditStatusTabBinding
import pro.breez.bfsut.databinding.LayoutCreditStatusTabBinding
import pro.breez.bfsut.model.CreditStatusEnum
import pro.breez.bfsut.ui.main.credit_status.CreditStatusFragment
import javax.inject.Inject

@AndroidEntryPoint
class CreditStatusTabFragment :
    BaseFragment<FragmentCreditStatusTabBinding, CreditStatusTabViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabs()
    }

    private fun initTabs() {
        val pagerAdapter = FragmentPagerItemAdapter(
            childFragmentManager, createTabs()
        )

        binding.tabLayout.setCustomTabView { container, position, adapter ->
            val customTabBinding =
                LayoutCreditStatusTabBinding.inflate(layoutInflater, container, false)
            val title = adapter.getPageTitle(position).toString()
            when (position) {
                0 -> {
                    customTabBinding.setTabInfo(R.drawable.ic_status_all, title)
                }
                1 -> {
                    customTabBinding.setTabInfo(R.drawable.ic_status_sent, title)
                }
                2 -> {
                    customTabBinding.setTabInfo(R.drawable.ic_status_commit, title)
                }
                3 -> {
                    customTabBinding.setTabInfo(R.drawable.ic_status_accepted, title)
                }
                4 -> {
                    customTabBinding.setTabInfo(R.drawable.selector_credit_status_denied, title)
                }
                else -> {
                    throw IllegalStateException("Wrong position in tabLayout ${this.javaClass.simpleName}")
                }
            }
            return@setCustomTabView customTabBinding.root
        }
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = pagerAdapter.count
        binding.tabLayout.setViewPager(binding.viewPager)
    }

    private fun createTabs(): FragmentPagerItems {
        val creator = FragmentPagerItems.with(requireContext())
        val statusFragments =
            arrayOf(
                Triple("Все", CreditStatusFragment::class.java, CreditStatusEnum.ALL),
                Triple("Отправлено", CreditStatusFragment::class.java, CreditStatusEnum.SENT),
                Triple("Коммитет", CreditStatusFragment::class.java, CreditStatusEnum.COMMIT),
                Triple("Одобрено", CreditStatusFragment::class.java, CreditStatusEnum.ACCEPTED),
                Triple("Отказ", CreditStatusFragment::class.java, CreditStatusEnum.DENIED),
            )
        for (triple in statusFragments) {
            creator.add(triple.first, triple.second, Bundle().apply {
                putSerializable(CreditStatusFragment.STATUS_KEY, triple.third)
            })
        }
        return creator.create()
    }

    private fun LayoutCreditStatusTabBinding.setTabInfo(@DrawableRes img: Int, title: String) {
        this.customTabText.text = title
        this.customTabText.setCompoundDrawablesWithIntrinsicBounds(img, 0, 0, 0)
    }

}

@HiltViewModel
class CreditStatusTabViewModel @Inject constructor() : BaseViewModel()