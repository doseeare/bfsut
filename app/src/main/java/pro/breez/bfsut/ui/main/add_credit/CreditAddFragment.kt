package pro.breez.bfsut.ui.main.add_credit

import android.os.Bundle
import android.text.InputType
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditAddBinding
import pro.breez.bfsut.util.ifNotNull
import pro.breez.bfsut.util.ifTrue
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.bfsut.util.validator.CreditAddFieldValidator

@AndroidEntryPoint
class CreditAddFragment : BaseFragment<FragmentCreditAddBinding, CreditAddViewModel>() {
    private lateinit var validator: CreditAddFieldValidator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validator = CreditAddFieldValidator(binding)
        initViews()
        initObserver()
    }

    private fun initObserver() = with(viewModel) {
        farmerLV.observe(viewLifecycleOwner) {
            binding.farmer.text = buildString {
                it.father_name.ifNotNull { append("$it ") }
                it.first_name.ifNotNull { append("$it ") }
                it.last_name.ifNotNull { append("$it ") }
            }
        }
        productLV.observe(viewLifecycleOwner) {
            it?.let { binding.product.text = it.name }
        }
        categoryLV.observe(viewLifecycleOwner) {
            binding.category.text = it.name
        }
        goalLV.observe(viewLifecycleOwner) {
            binding.goal.text = it.name
            binding.product.reset()
        }
        periodLV.observe(viewLifecycleOwner) {
            binding.period.text = it.period
        }
        dateOfPaymentLV.observe(viewLifecycleOwner) {
            binding.dateOfPayment.text = it
        }
        dateDisburseLV.observe(viewLifecycleOwner) {
            binding.dateDisburseEt.text = it
        }
    }

    private fun initViews() = with(binding) {
        validator.enableSendBtnOnEdit()
        toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
        farmer.setOnClickListener {
            viewModel.showSearchDialog(true)
        }
        goal.setOnClickListener {
            viewModel.goalClicked()
        }
        product.setOnClickListener {
            viewModel.productClicked()
        }
        category.setOnClickListener {
            viewModel.categoryClicked()
        }
        period.setOnClickListener {
            viewModel.periodClicked()
        }
        sendBtn.setOnClickOnceListener {
            viewModel.sendBtnClicked(validator.validateFields())
        }
        commentsOfGoal.onTextChanged = {
            viewModel.commentOfGoal.postEvent(it)
        }
        dateOfPayment.setOnClickListener {
            viewModel.dateOfPaymentClicked()
        }
        dateDisburseEt.setOnClickListener {
            viewModel.dateDisburseClicked()
        }
        sum.editText.inputType = InputType.TYPE_CLASS_NUMBER
        sum.onTextChanged = {
            it.isNotBlank().ifTrue {
                viewModel.sumLV.postEvent(it.toFloat().toString())
            }
        }
    }
}