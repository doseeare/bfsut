package pro.breez.bfsut.ui.main.add_credit

import android.os.Bundle
import android.text.InputType
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditAddBinding
import pro.breez.bfsut.util.ifTrue
import pro.breez.bfsut.util.setOnClickOnceListener
import pro.breez.bfsut.util.validator.CreditAddFieldValidator

@AndroidEntryPoint
class CreditAddFragment : BaseFragment<FragmentCreditAddBinding, CreditAddViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }

    private fun initObserver() = with(viewModel) {
        farmerLV.observe(viewLifecycleOwner) {
            binding.farmer.text =
                "${it.firstName} ${it.fatherName} ${it.lastName}"
                    .replace("  ", " ")
        }
        productLV.observe(viewLifecycleOwner) {
            binding.product.text = it.name
        }
        categoryLV.observe(viewLifecycleOwner) {
            binding.category.text = it.name
        }
        goalLV.observe(viewLifecycleOwner) {
            binding.goal.text = it.name
        }
        periodLV.observe(viewLifecycleOwner) {
            binding.date.text = it
        }
        dateOfPaymentLV.observe(viewLifecycleOwner) {
            binding.dateOfPayment.text = it
        }
    }

    private fun initViews() = with(binding) {
        toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
        farmer.setOnClickListener {
            viewModel.showSearchDialog(false)
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
        date.setOnClickListener {
            viewModel.periodClicked()
        }
        sendBtn.setOnClickOnceListener {
            val validator = CreditAddFieldValidator(binding)
            viewModel.sendBtnClicked(validator.validateFields())
        }
        commentsOfGoal.onTextChanged = {
            viewModel.commentOfGoal.postEvent(it)
        }
        dateOfPayment.setOnClickListener {
            viewModel.dateOfPaymentClicked()
        }
        sum.editText.inputType = InputType.TYPE_CLASS_NUMBER
        sum.onTextChanged = {
            it.isNotBlank().ifTrue {
                viewModel.sum.postEvent(it.toFloat().toString())
            }
        }
    }
}