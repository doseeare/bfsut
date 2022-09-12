package pro.breez.bfsut.ui.auth.activity

import android.os.Bundle
import android.view.LayoutInflater
import pro.breez.bfsut.base.BaseActivity
import pro.breez.bfsut.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityAuthBinding.inflate(layoutInflater)
}