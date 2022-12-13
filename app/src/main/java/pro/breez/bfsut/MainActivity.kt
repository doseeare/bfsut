package pro.breez.bfsut

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseActivity
import pro.breez.bfsut.databinding.ActivityMainBinding
import pro.breez.bfsut.model.navigation.FragmentTransaction
import pro.breez.bfsut.util.setOnClickOnceListener


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navBagde: BadgeDrawable by lazy {
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
            navigateToFragment(FragmentTransaction(R.id.navigation_farmer_add))
        }

        binding.addCredit.setOnClickOnceListener {
            fabSelected = false
            navigateToFragment(FragmentTransaction(R.id.navigation_credit_add))
        }

        binding.shadow.setOnClickOnceListener {
            fabSelected = !fabSelected
        }
    }

    fun setBadge(count: Int) {
        if (count == 0) {
            binding.navView.removeBadge(R.id.navigation_credits)
        } else {
            navBagde.apply {
                number = count
                backgroundColor = getColor(R.color.error_color)
                badgeTextColor = getColor(R.color.white)
            }
        }
        Log.d("BADGE", "setBadge: $count")
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)
}