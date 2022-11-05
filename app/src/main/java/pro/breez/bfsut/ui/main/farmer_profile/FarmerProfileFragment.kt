package pro.breez.bfsut.ui.main.farmer_profile

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentFarmerProfileBinding
import pro.breez.bfsut.model.GenderEnum

@AndroidEntryPoint
class FarmerProfileFragment : BaseFragment<FragmentFarmerProfileBinding, FarmerProfileViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() = with(binding) {
        toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
        toolbar.setOnOptionClickedListener {
            viewModel.editClicked()
        }
    }

    private fun initObservers() = with(viewModel) {
        farmerProfileLV.observe(viewLifecycleOwner) {
            binding.apply {
                nameTv.text = "${it.first_name} ${it.father_name} ${it.last_name}" //todo спросить
                lastNameTv.text = it.father_name
                firstNameTv.text = it.first_name
                surnameTv.text = it.last_name
                birthdayTv.text = it.birth_date
                nationTv.text = it.nationality.toString() //todo спросить
                citizenTv.text = it.resident.toString() //todo спросить
                genderTv.text = GenderEnum.fromInt(it.gender!!).value //todo спросить
                morePhoneTv.text = it.phone_number
                moreSecondPhoneTv.text = it.phone_number_additional
                innTv.text = it.tax_number
                docTypeTv.text = it.document_type.toString() //todo спросить
                docSeriesTv.text = it.document_series
                docNumberTv.text = it.document_number //todo спросить
                docIssueTv.text = it.document_issue.toString()//todo спросить
                docWhenIssueTv.text = it.document_issue_number //todo спросить
                countryTv.text = it.state.toString() //todo спросить
                areaTv.text = it.country.toString() //todo спросить
                regionTv.text = it.region.toString()
                streetTv.text = "Не указан" //todo спросить
                villageTv.text = it.village
                houseTv.text = it.house
                apartmentTv.text = it.apartment
                familyStatus.text = it.marital_status
                namePartnerTv.text = "Не указан" //todo спросить
                familyMembersTv.text = it.family_count.toString() //todo спросить
                jobTv.text = it.job
                jobPurposeTv.text = it.job_position
                jobAddressTv.text = it.job_address
                educationLevelTv.text = it.education.toString() //todo спросить
            }
        }
    }

}