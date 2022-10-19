package pro.breez.bfsut.ui.main.add_farmer

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseFragment
import pro.breez.bfsut.custom.view.SelectableButton
import pro.breez.bfsut.databinding.FragmentAddFarmerBinding
import pro.breez.bfsut.model.GenderEnum
import pro.breez.bfsut.model.MaritalStatusEnum
import pro.breez.bfsut.util.Utils.setNumberMask
import pro.breez.bfsut.util.setOnClickOnceListener


@AndroidEntryPoint
class AddFarmerFragment : BaseFragment<FragmentAddFarmerBinding, AddFarmerViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() = with(binding) {
        toolbar.setOnBackClickListener {
            requireActivity().onBackPressed()
        }
        toolbar.setTitle("Создание фермера")
        SelectableButton.init(genderMale, genderFemale) {
            viewModel.genderSelected(GenderEnum.fromId(it.id))
        }
        SelectableButton.init(marriedBtn, singleBtn, widowerBtn, divorcedBtn) {
            viewModel.maritalSelected(MaritalStatusEnum.fromId(it.id))
        }
        phoneNumberEt.editText.setNumberMask()
        phoneNumberMoreEt.editText.setNumberMask()

        actualLocationYes.setOnClickOnceListener {
            initButtons(actualLocationYes, actualLocationNo)
        }
        actualLocationNo.setOnClickOnceListener {
            initButtons(actualLocationNo, actualLocationYes)
        }
        innEt.editText.filters = arrayOf<InputFilter>(LengthFilter(14))
        innEt.editText.inputType = InputType.TYPE_CLASS_NUMBER

        birthdayEt.setOnClickListener(viewModel::birthDayClicked)
        nationEt.setOnClickListener(viewModel::nationClicked)
        citizenEt.setOnClickListener(viewModel::citizenClicked)
        typeDocEt.setOnClickListener(viewModel::docTypeClicked)
        seriesDocEt.setOnClickListener(viewModel::docSeriesClicked)
        issueDocEt.setOnClickListener(viewModel::docIssueClicked)
        whenDocEt.setOnClickListener(viewModel::whenDocClicked)
        educationEt.setOnClickListener(viewModel::educationClicked)
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
    }

    private fun initAcceptBtn() = with(binding) {
        viewModel.apply {
            name = nameEt.text
            lastname = lastNameEt.text
            surname = surnameEt.text
            phoneNumber = phoneNumberEt.text
            phoneNumberMore = phoneNumberMoreEt.text
            phoneNumberComfort = phoneNumberComfortEt.text
            INN = innEt.text
            docNumber = numberDocEt.text
            village = villageEt.text
            street = streetEt.text
            house = houseEt.text
            apartment = apartmentEt.text
            jobCompany = jobCompanyEt.text
            jobName = jobEt.text
            jobAddress = jobAddressEt.text
            initAcceptClicked()
        }
    }

    private fun initObservers() {
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
        viewModel.whenDocLV.observe(viewLifecycleOwner) {
            binding.whenDocEt.text = it
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
    }

    private fun initButtons(selectedBtn: AppCompatButton, alternativeBtn: AppCompatButton) {
        selectedBtn.apply {
            if (isSelected) return@apply
            isSelected = true
            setTextColor(ContextCompat.getColor(context, R.color.white))
            alternativeBtn.isSelected = false
            alternativeBtn.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.text_bold_color
                )
            )
        }
        when (selectedBtn.id) {
            binding.actualLocationYes.id -> {
                binding.actualLocationContainer.visibility = View.VISIBLE
                viewModel.isActualLocation = true
            }
            binding.actualLocationNo.id -> {
                binding.actualLocationContainer.visibility = View.GONE
                viewModel.isActualLocation = false
            }
        }
    }
}