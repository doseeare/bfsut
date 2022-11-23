package pro.breez.bfsut.ui.issued_info

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentIssuedInfoBinding

@AndroidEntryPoint
class IssuedInfoFragment : BaseFragment<FragmentIssuedInfoBinding, IssuedInfoViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        viewModel.creditId = requireArguments().getString(KEY, "")
        viewModel.getIssuedDetail()
        viewModel.issuedDetailLV.observe(viewLifecycleOwner) {
            nameTv.text = it.full_name
            creditSumTv.text = "${it.amount} с"
            creditPeriodTv.text = "${it.period} мес."
            overdueDayTv.text = "0 дней" //спросить
            branchTv.text = it.branch
            officeTv.text = it.office
            creditOfficerTv.text = it.credit_officer
            goalTv.text = it.purpose
            commentTv.text = it.purpose_comment
            productTv.text = it.product_bank
            categoryTv.text = it.category
            paymentDateTv.text = "${it.date_pay} число"
            sumTv.text = "${it.amount} с"
            percentTv.text = "${it.percent_per_year}% в год"
            phoneNumberTv.text = it.phone_number
            countryTv.text = it.state
            stateTv.text = it.country
            regionTv.text = it.region
            villageTv.text = it.village
            houseTv.text = it.house
            apartmentTv.text = it.apartment
            streetTv.text = it.address
            partnerNameTv.text = it.spouse_full_name
            binding.warningTv.visibility = View.VISIBLE
            binding.warningTv.text =
                "${it.start_date} - ${it.start_date}\nПогашение каждое ${it.date_pay} число\n"
        }
    }


    companion object {
        const val KEY = "IssuedInfoFragment"
    }

}