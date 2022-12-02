package pro.breez.bfsut.ui.auth.fragment.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentAuthBinding
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding, AuthViewModel>() {

    private var fieldsState = Pair(false, false)
        set(value) {
            field = value
            binding.enterBtn.isEnabled = value.first && value.second
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        binding.enterBtn.setOnClickOnceListener {
            val login = loginEt.text
            val password = passwordEt.text
            viewModel.login(login to password)
        }

        viewModel.fieldsError.observe(viewLifecycleOwner) {
            loginEt.showError = true
            passwordEt.showError = true
        }

        loginEt.editText.doOnTextChanged { text, _, _, _ ->
            fieldsState = Pair(text.toString().isNotBlank(), fieldsState.second)
            loginEt.showError = false
        }
        passwordEt.editText.doOnTextChanged { text, _, _, _ ->
            fieldsState = Pair(fieldsState.first, text.toString().isNotBlank())
            passwordEt.showError = false
        }
    }


}