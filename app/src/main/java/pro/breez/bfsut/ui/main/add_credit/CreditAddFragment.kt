package pro.breez.bfsut.ui.main.add_credit

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentCreditAddBinding

@AndroidEntryPoint
class CreditAddFragment : BaseFragment<FragmentCreditAddBinding, CreditAddViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
    }

    private fun initObserver() = with(viewModel) {
        farmerLV.observe(viewLifecycleOwner) {
            binding.farmer.text = "${it.first_name} ${it.father_name} ${it.last_name}"
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
        dateLV.observe(viewLifecycleOwner) {
            binding.date.text = it.second
        }
    }

    private fun initViews() = with(binding) {
        toolbar.setOnBackClickListener {
            viewModel.popBackStack.startEvent(R.id.navigation_home)
        }
        farmer.onClicked = {
            viewModel.farmerClicked()
        }
        goal.onClicked = {
            viewModel.goalClicked()
        }
        product.onClicked = {
            viewModel.productClicked()
        }
        category.onClicked = {
            viewModel.categoryClicked()
        }
   /*     date.onClicked = {
            viewModel.dateClicked()
        }*/

        sendBtn.setOnClickListener {
            viewModel.sendBtnClicked(fieldsNotEmpty())
        }

        commentsOfGoal.onTextChanged = {
            viewModel.commentOfGoal.postEvent(it)
        }
        dateOfPayment.onTextChanged = {
            viewModel.dateOfPayment.postEvent(it)
        }
        sum.onTextChanged = {
            viewModel.sum.postEvent(it)
        }
    }

    private fun fieldsNotEmpty(): Boolean {
        val fields = listOf(
            binding.farmer,
            binding.goal,
            binding.commentsOfGoal,
            binding.product,
            binding.category,
            binding.date,
            binding.dateOfPayment,
            binding.sum
        )
        for (field in fields) {
            if (field.isFieldEmpty) return false
        }
        return true
    }
}