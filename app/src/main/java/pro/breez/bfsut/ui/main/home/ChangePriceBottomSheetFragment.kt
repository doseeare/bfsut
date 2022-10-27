package pro.breez.bfsut.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseBottomSheetFragment
import pro.breez.bfsut.databinding.FragmentChangePriceBottomSheetBinding
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class ChangePriceBottomSheetFragment :
    BaseBottomSheetFragment<FragmentChangePriceBottomSheetBinding, HomeViewModel>() {

    private var currentPrice: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPrice = requireArguments().getInt(MILK_PRICE_KEY)
        initViews()
    }

    private fun initViews() {
        binding.priceTv.text = "Текущая цена: $currentPrice сом за литр"
        binding.priceEt.hint = "$currentPrice"

        binding.priceEt.doOnTextChanged { text, start, before, count ->
            binding.priceEt.isEnabled = text.toString().isNotBlank()
        }
        binding.cancelBtn.setOnClickOnceListener {
            dismiss()
        }

        binding.editBtn.setOnClickOnceListener {
            val newPrice = binding.priceEt.text.toString().toInt()
            viewModel.changeMilkPrice(newPrice) {
                (requireParentFragment() as HomeFragment).refreshMilkPrice()
                dismiss()
            }
        }
    }

    override fun getTheme(): Int {
        return pro.breez.bfsut.R.style.CustomBottomSheetDialog
    }

    companion object {
        const val MILK_PRICE_KEY = "MILK_PRICE_KEY"
        fun newInstance(currentPrice: Int): ChangePriceBottomSheetFragment {
            val bundle = Bundle()
            val fragment = ChangePriceBottomSheetFragment()
            bundle.putInt(MILK_PRICE_KEY, currentPrice)
            fragment.arguments = bundle
            return fragment
        }
    }

}
