package pro.breez.bfsut.ui.auth.activity

import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseActivity
import pro.breez.bfsut.databinding.ActivityAuthBinding

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityAuthBinding.inflate(layoutInflater)
}