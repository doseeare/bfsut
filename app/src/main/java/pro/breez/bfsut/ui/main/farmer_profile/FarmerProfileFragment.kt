package pro.breez.bfsut.ui.main.farmer_profile

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.databinding.FragmentFarmerProfileBinding
import pro.breez.bfsut.util.ifFalse
import pro.breez.bfsut.util.ifNotNull
import pro.breez.bfsut.util.setOnClickOnceListener

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
        milkHistoryBtn.setOnClickOnceListener {
            viewModel.historyClicked()
        }
    }

    private fun initObservers() = with(viewModel) {
        farmerProfileLV.observe(viewLifecycleOwner) {
            binding.apply {
                nameTv.text = buildString {
                    it.father_name.ifNotNull { append("$it ") }
                    it.first_name.ifNotNull { append("$it ") }
                    it.last_name.ifNotNull { append("$it ") }
                }
                staticsPerTv.text = getString(R.string.static_per_month, 6)
                lastNameTv.text = it.father_name
                firstNameTv.text = it.first_name
                surnameTv.text = it.last_name
                birthdayTv.text = it.birth_date
                nationTv.text = it.nationality.toString()
                citizenTv.text = it.resident.toString()
                genderTv.text = it.gender
                morePhoneTv.text = it.phone_number
                moreSecondPhoneTv.text = it.phone_number_additional
                innTv.text = it.tax_number
                docTypeTv.text = it.document_type.toString()
                docSeriesTv.text = it.document_series
                docNumberTv.text = it.document_number
                docIssueTv.text = it.document_issue.toString()
                docWhenIssueTv.text = it.document_issue_number
                countryTv.text = it.state.toString()
                areaTv.text = it.country.toString()
                regionTv.text = it.region.toString()
                streetTv.text = it.address
                villageTv.text = it.village
                houseTv.text = it.house
                apartmentTv.text = it.apartment
                familyStatus.text = it.marital_status
                jobTv.text = it.job
                jobPurposeTv.text = it.job_position
                jobAddressTv.text = it.job_address
                educationLevelTv.text = it.education.toString()
                morningPanel.setLiters(it.morning_milk_amount?.toInt())
                morningPanel.setSum(it.morning_price_sum?.toInt())
                eveningPanel.setLiters(it.evening_milk_amount?.toInt())
                eveningPanel.setSum(it.evening_price_sum?.toInt())
                it.is_actual_address_match?.ifFalse {
                    actualAddressContainer.visibility = View.VISIBLE
                    actualCountryTv.text = it.actual_state.toString()
                    actualAreaTv.text = it.actual_country.toString()
                    actualRegionTv.text = it.actual_region.toString()
                    actualStreetTv.text = it.actual_address
                    actualVillageTv.text = it.actual_village
                    actualHouseTv.text = it.actual_house
                    actualApartmentTv.text = it.actual_apartment
                }
            }
        }
    }

}