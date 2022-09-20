package pro.breez.bfsut

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseActivity
import pro.breez.bfsut.databinding.ActivityMainBinding
import pro.breez.bfsut.model.navigation.FragmentTransaction


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var fabSelected: Boolean = false
        set(value) {
            field = value
            if (value) {
                binding.addMenu.visibility = View.VISIBLE
                binding.fabBtn.foreground =
                    ContextCompat.getDrawable(this, R.drawable.bg_add_close)
            } else {
                binding.addMenu.visibility = View.GONE
                binding.fabBtn.foreground =
                    ContextCompat.getDrawable(this, R.drawable.bg_add_main)
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

        binding.fabBtn.setOnClickListener {
            fabSelected = !fabSelected
        }

        binding.addCredit.setOnClickListener {
            fabSelected = false
            navigateToFragment(FragmentTransaction(R.id.navigation_credit_add))
        }
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)
}