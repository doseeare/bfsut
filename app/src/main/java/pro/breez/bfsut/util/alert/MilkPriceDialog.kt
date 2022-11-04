package pro.breez.bfsut.util.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.DialogMilkPriceBinding

class MilkPriceDialog(private val currentPrice: Int) : DialogFragment() {
    private lateinit var binding: DialogMilkPriceBinding

    private var onPositiveClick: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMilkPriceBinding.inflate(inflater, container, false)
        requireDialog().window?.setBackgroundDrawableResource(android.R.color.transparent)
        requireDialog().setCancelable(false)
        if (currentPrice != 0) {
            binding.title.text = getString(R.string.old_price_or_new)
        }
        binding.priceEt.hint = "$currentPrice"
        binding.positiveBtn.setOnClickListener(onPositiveClick)
        return binding.root
    }

    fun onPositiveBtnClicked(block: (Int) -> Unit) {
        onPositiveClick = View.OnClickListener {
            val price = binding.priceEt.text.toString()
            val newPrice = if (price.isNotBlank()) {
                price.toInt()
            } else {
                currentPrice
            }
            block.invoke(newPrice)
        }
    }

}