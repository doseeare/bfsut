package pro.breez.bfsut.ui.main.add

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
            binding.farmer.text = it.second
        }
        productLV.observe(viewLifecycleOwner) {
            binding.product.text = it.second
        }
        categoryLV.observe(viewLifecycleOwner) {
            binding.category.text = it.second
        }
        goalLV.observe(viewLifecycleOwner) {
            binding.goal.text = it.second
        }
        dateLV.observe(viewLifecycleOwner) {
            binding.date.text = it.second
        }

    }

    private fun initViews() = with(binding) {
        toolbarBackBtn.setOnClickListener {
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
        date.onClicked = {
            viewModel.dateClicked()
        }

        sendBtn.setOnClickListener {
            viewModel.sendBtnClicked()
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
}