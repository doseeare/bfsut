package pro.breez.bfsut.ui.main.credit_status

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditStatusBinding

@AndroidEntryPoint
class CreditStatusFragment : BaseFragment<FragmentCreditStatusBinding, CreditStatusViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        const val STATUS_KEY = "credit_status_key"
    }

}