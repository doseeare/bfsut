package pro.breez.bfsut.ui.main.credit_status_detail

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditStatusDetailBinding
import pro.breez.bfsut.model.CreditStatusEnum
import pro.breez.bfsut.util.setOnClickOnceListener

@AndroidEntryPoint
class CreditStatusDetailFragment :
    BaseFragment<FragmentCreditStatusDetailBinding, CreditStatusDetailViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        viewModel.creditDetailLV.observe(viewLifecycleOwner) {
            val statusEnum = CreditStatusEnum.fromKey(it.status)
            statusTv.text = statusEnum.title
            statusTv.setCompoundDrawablesWithIntrinsicBounds(statusEnum.icon, 0, 0, 0)

            toolbarBackBtn.setOnClickOnceListener {
                requireActivity().onBackPressed()
            }

            when (statusEnum) {
                CreditStatusEnum.ACCEPTED -> {
                    warningContainer.setBackgroundResource(R.drawable.bg_credit_status_green)
                    warningTv.text = buildString {
                        append(
                            "Ура! Заявка фемера ${it.full_name} была одобрена," +
                                    " он сможет получить ${it.amount} сом. Свяжитесь с нашими специалистами"
                        )
                    }
                }
                CreditStatusEnum.DENIED -> {
                    warningContainer.setBackgroundResource(R.drawable.bg_credit_status_red)
                    warningTv.text = buildString {
                        append("К сожалению заявка фермера ${it.full_name} на ${it.amount} сом была отклонена")
                    }
                }
                else -> {
                    warningContainer.visibility = View.GONE
                }
            }

            closeWarningBtn.setOnClickOnceListener {
                warningContainer.visibility = View.GONE
            }

            nameTv.text = it.full_name
            creditSumTv.text = it.amount
            creditPeriodTv.text = "${it.period} мес."
            branchTv.text = it.branch
            officeTv.text = it.office
            creditOfficerTv.text = it.credit_officer
            goalTv.text = it.purpose
            commentTv.text = it.purpose_comment
            productTv.text = it.product_bank
            categoryTv.text = it.category
            paymentDateTv.text = "${it.date_pay} число"
            sumTv.text = it.amount
            percentTv.text = it.percent_per_year
            overallTv.text = "Примерно ${it.monthly_payment_amount.toDouble().toInt()} с/мес."
        }
    }

}