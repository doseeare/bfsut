package pro.breez.bfsut.ui.main.farmer_profile_edit

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.custom.view.SelectableButton
import pro.breez.bfsut.databinding.FragmentFarmerProfileEditBinding
import pro.breez.bfsut.model.GenderEnum
import pro.breez.bfsut.model.MaritalStatusEnum
import pro.breez.bfsut.util.Utils.setNumberMask
import pro.breez.bfsut.util.ifNotNull
import pro.breez.bfsut.util.validator.ProfileEditValidator
import pro.breez.bfsut.util.visibility

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentFarmerProfileEditBinding, EditProfileViewModel>() {
    private lateinit var validator: ProfileEditValidator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validator = ProfileEditValidator(binding, viewModel)
        initViews()
        initObservers()
    }

    private fun initObservers() = with(viewModel) {
        farmerProfileLV.observe(viewLifecycleOwner) {
            binding.apply {
                lastNameEt.editText.setText(it.father_name)
                nameEt.editText.setText(it.first_name)
                surnameEt.editText.setText(it.last_name)
                birthdayEt.editText.setText(it.birth_date)
                nationEt.editText.setText(it.nationality)
                citizenEt.editText.setText(it.resident)
                phoneNumberEt.editText.setText(it.phone_number)
                phoneNumberMoreEt.editText.setText(it.phone_number_additional)
                innEt.editText.setText(it.tax_number)
                typeDocEt.editText.setText(it.document_type)
                seriesDocEt.editText.setText(it.document_series)
                numberDocEt.editText.setText(it.document_number)
                issueDocEt.editText.setText(it.document_issue)
                issueNumberDocEt.editText.setText(it.document_issue_number)
                whenDocEt.editText.setText(it.document_date)
                placeOfBirthEt.editText.setText(it.place_of_birth)
                countryEt.editText.setText(it.state)
                areaEt.editText.setText(it.country)
                regionEt.editText.setText(it.region)
                streetEt.editText.setText(it.address)
                villageEt.editText.setText(it.village)
                houseEt.editText.setText(it.house)
                apartmentEt.editText.setText(it.apartment)
                familyMembersEt.editText.setText(it.family_count.toString())
                jobEt.editText.setText(it.job)
                jobPurposeEt.editText.setText(it.purpose)
                jobAddressEt.editText.setText(it.job_address)
                jobCompanyEt.editText.setText(it.job_position)
                educationEt.editText.setText(it.education)
                actualCountryEt.editText.setText(it.actual_state)
                actualAreaEt.editText.setText(it.actual_country)
                actualRegionEt.editText.setText(it.actual_region)
                actualVillageEt.editText.setText(it.actual_village)
                actualStreetEt.editText.setText(it.actual_address)
                actualHouseEt.editText.setText(it.actual_house)
                actualApartmentEt.editText.setText(it.actual_apartment)

                viewModel.isActualLocation?.ifNotNull {
                    binding.actualLocationContainer.visibility(!it)
                    if (it) {
                        actualLocationYes.isSelected = true
                    } else {
                        actualLocationNo.isSelected = true
                    }
                }

                when (it.marital_status) {
                    MaritalStatusEnum.MARRIED.value -> {
                        marriedBtn.isActive = true
                        viewModel.maritalStatus = MaritalStatusEnum.MARRIED
                    }
                    MaritalStatusEnum.SINGLE.value -> {
                        singleBtn.isActive = true
                        viewModel.maritalStatus = MaritalStatusEnum.SINGLE
                    }
                    MaritalStatusEnum.DIVORCED.value -> {
                        divorcedBtn.isActive = true
                        viewModel.maritalStatus = MaritalStatusEnum.SINGLE
                    }
                    MaritalStatusEnum.WIDOWED.value -> {
                        widowerBtn.isActive = true
                        viewModel.maritalStatus = MaritalStatusEnum.DIVORCED
                    }
                }
                when (it.gender) {
                    GenderEnum.MALE.value -> {
                        genderMale.isActive = true
                        viewModel.gender = GenderEnum.MALE
                    }
                    GenderEnum.FEMALE.value -> {
                        genderFemale.isActive = true
                        viewModel.gender = GenderEnum.FEMALE
                    }
                }
            }
            validator.fieldsEdited = false
            validator.checkAcceptBtn()
        }
        viewModel.birthdayLV.observe(viewLifecycleOwner) {
            binding.birthdayEt.text = it
        }
        viewModel.nationalityLV.observe(viewLifecycleOwner) {
            binding.nationEt.text = it.name
        }
        viewModel.citizenLV.observe(viewLifecycleOwner) {
            binding.citizenEt.text = it.name
        }
        viewModel.docTypesLV.observe(viewLifecycleOwner) {
            binding.typeDocEt.text = it.name
        }
        viewModel.docSeriesLV.observe(viewLifecycleOwner) {
            binding.seriesDocEt.text = it.name
        }
        viewModel.docIssueLV.observe(viewLifecycleOwner) {
            binding.issueDocEt.text = it.name
        }
        viewModel.docWhenLV.observe(viewLifecycleOwner) {
            binding.whenDocEt.editText.setText(it)
        }
        viewModel.areaLv.observe(viewLifecycleOwner) {
            binding.areaEt.text = it.name
        }
        viewModel.countryLV.observe(viewLifecycleOwner) {
            binding.countryEt.text = it.name
        }
        viewModel.regionLV.observe(viewLifecycleOwner) {
            binding.regionEt.text = it.name
        }
        viewModel.actualRegionLV.observe(viewLifecycleOwner) {
            binding.actualRegionEt.text = it.name
        }
        viewModel.actualCountryLV.observe(viewLifecycleOwner) {
            binding.actualCountryEt.text = it.name
        }
        viewModel.actualAreaLv.observe(viewLifecycleOwner) {
            binding.actualAreaEt.text = it.name
        }
        viewModel.educationLV.observe(viewLifecycleOwner) {
            binding.educationEt.text = it.name
        }
        viewModel.jobPurposeLV.observe(viewLifecycleOwner) {
            binding.jobPurposeEt.text = it.name
        }
        viewModel.farmerFoundLV.observe(viewLifecycleOwner) {
            binding.nameEt.text = it.firstName
            binding.lastNameEt.text = it.fatherName
            binding.surnameEt.text = it.lastName
        }
    }

    private fun initViews() = with(binding) {
        toolbar.setOnBackClickListener {
            viewModel.backBtnClicked()
        }
        SelectableButton.init(marriedBtn, singleBtn, widowerBtn, divorcedBtn) {
            viewModel.maritalSelected(MaritalStatusEnum.fromId(it.id))
        }
        SelectableButton.init(genderMale, genderFemale) {
            viewModel.genderSelected(GenderEnum.fromId(it.id))
        }
        phoneNumberEt.editText.setNumberMask()
        phoneNumberMoreEt.editText.setNumberMask()

        initButtons(actualLocationYes, actualLocationNo)
        initButtons(actualLocationNo, actualLocationYes)

        innEt.editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(14))
        innEt.editText.inputType = InputType.TYPE_CLASS_NUMBER

        issueNumberDocEt.editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(7))
        issueNumberDocEt.editText.inputType = InputType.TYPE_CLASS_NUMBER

        numberDocEt.editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(7))
        numberDocEt.editText.inputType = InputType.TYPE_CLASS_NUMBER

        birthdayEt.setOnClickListener(viewModel::birthDayClicked)
        nationEt.setOnClickListener(viewModel::nationClicked)
        citizenEt.setOnClickListener(viewModel::citizenClicked)
        typeDocEt.setOnClickListener(viewModel::docTypeClicked)
        seriesDocEt.setOnClickListener(viewModel::docSeriesClicked)
        issueDocEt.setOnClickListener(viewModel::docIssueClicked)
        whenDocEt.setOnClickListener(viewModel::whenDocClicked)
        educationEt.setOnClickListener(viewModel::educationClicked)
        jobPurposeEt.setOnClickListener(viewModel::jobPurposeClicked)

        countryEt.setOnClickListener {
            viewModel.countryClicked(false)
        }
        areaEt.setOnClickListener {
            viewModel.areaClicked(false)
        }
        regionEt.setOnClickListener {
            viewModel.regionClicked(false)
        }
        actualCountryEt.setOnClickListener {
            viewModel.countryClicked(true)
        }
        actualAreaEt.setOnClickListener {
            viewModel.areaClicked(true)
        }
        actualRegionEt.setOnClickListener {
            viewModel.regionClicked(true)
        }
        validator.initValidateListeners()
    }

    private fun initButtons(selectedBtn: AppCompatButton, alternativeBtn: AppCompatButton) {
        selectedBtn.apply {
            setOnClickListener {
                setBackgroundResource(R.drawable.selector_selectable_button)
                alternativeBtn.setBackgroundResource(R.drawable.selector_selectable_button)
                if (isSelected) return@setOnClickListener
                isSelected = true
                setTextColor(ContextCompat.getColor(context, R.color.white))
                alternativeBtn.isSelected = false
                alternativeBtn.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.text_bold_color
                    )
                )
                when (id) {
                    binding.actualLocationYes.id -> {
                        binding.actualLocationContainer.visibility = View.GONE
                        viewModel.isActualLocation = true
                    }
                    binding.actualLocationNo.id -> {
                        binding.actualLocationContainer.visibility = View.VISIBLE
                        viewModel.isActualLocation = false
                    }
                }
                binding.acceptBtn.isEnabled = true
            }
        }
    }

}