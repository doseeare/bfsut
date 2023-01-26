package pro.breez.bfsut.ui.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseActivity
import pro.breez.bfsut.databinding.ActivityMainBinding
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.ui.main.credit.CreditsFragment
import pro.breez.bfsut.ui.main.home.HomeFragment
import pro.breez.bfsut.ui.main.log.LogFragment
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navBadge: BadgeDrawable by lazy {
        binding.navView.getOrCreateBadge(R.id.navigation_credits)
    }

    var fabSelected: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.addMenu.visibility = View.VISIBLE
                binding.fabBtn.foreground =
                    ContextCompat.getDrawable(this, R.drawable.bg_add_close)
                binding.shadow.visibility = View.VISIBLE
            } else {
                binding.addMenu.visibility = View.GONE
                binding.fabBtn.foreground =
                    ContextCompat.getDrawable(this, R.drawable.bg_add_main)
                binding.shadow.visibility = View.GONE
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null

        val navController = findNavController(R.id.nav_host)
        navView.setupWithNavController(navController)

        navController.navigateUp()

        binding.fabBtn.setOnClickOnceListener {
            fabSelected = !fabSelected
        }
        binding.addMilk.setOnClickOnceListener {
            fabSelected = false
            navigateToFragment(FragmentTransaction(R.id.navigation_milk_add))
        }

        binding.addFarmer.setOnClickOnceListener {
            fabSelected = false
            creditAddFarmerSearch()
        }

        binding.addCredit.setOnClickOnceListener {
            fabSelected = false
            navigateToFragment(FragmentTransaction(R.id.navigation_credit_add))
        }

        binding.shadow.setOnClickOnceListener {
            fabSelected = !fabSelected
        }
    }

    private fun creditAddFarmerSearch() {
        supportFragmentManager.findFragmentById(R.id.nav_host)?.childFragmentManager?.let {
            it.fragments.first().apply {
                when (this) {
                    is HomeFragment -> this.creditAdd()
                    is LogFragment -> this.creditAdd()
                    is CreditsFragment -> this.creditAdd()
                }
            }
        }
    }

    fun setBadge(count: Int) {
        if (count == 0) {
            binding.navView.removeBadge(R.id.navigation_credits)
        } else {
            navBadge.apply {
                number = count
                backgroundColor = getColor(R.color.error_color)
                badgeTextColor = getColor(R.color.white)
            }
        }
    }

    fun showDivider(show: Boolean) {
        binding.divider.isVisible = show
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)
}