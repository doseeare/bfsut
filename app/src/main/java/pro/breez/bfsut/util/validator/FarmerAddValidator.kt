package pro.breez.bfsut.util.validator

import android.util.ArraySet
import androidx.collection.arraySetOf
import pro.breez.bfsut.R
import pro.breez.bfsut.custom.view.SelectableButton
import pro.breez.bfsut.databinding.FragmentAddFarmerBinding
import pro.breez.bfsut.ui.main.add_farmer.FarmerAddViewModel
import pro.breez.bfsut.util.ifFalse
import pro.breez.bfsut.util.ifTrue
import pro.breez.bfsut.util.isNull

class FarmerAddValidator(
    val binding: FragmentAddFarmerBinding,
    val viewModel: FarmerAddViewModel
) {
    private val rec = binding.root.context.resources

    private val checkedFields = ArraySet<String>()
    var fieldsEdited = false

    private val importantFields = arraySetOf(
        binding.nameEt, binding.lastNameEt, binding.birthdayEt,
        binding.nationEt, binding.citizenEt,
        binding.phoneNumberEt, binding.innEt,
        binding.typeDocEt, binding.seriesDocEt,
        binding.whenDocEt, binding.numberDocEt,
        binding.issueDocEt, binding.countryEt,
        binding.villageEt, binding.streetEt,
        binding.areaEt, binding.regionEt, binding.houseEt,
        binding.educationEt, binding.jobEt,
        binding.jobCompanyEt, binding.jobAddressEt,
        binding.issueNumberDocEt, binding.placeOfBirthEt,
        binding.jobPurposeEt
    )

    private val otherFields = arrayOf(
        binding.surnameEt, binding.phoneNumberMoreEt,
        binding.phoneNumberComfortEt, binding.apartmentEt,
        binding.actualApartmentEt, binding.actualStreetEt,
        binding.actualVillageEt
    )

    private val maritalBtn = arrayOf(
        binding.marriedBtn, binding.singleBtn,
        binding.widowerBtn, binding.divorcedBtn,
    )

    private val genderBtn = arrayOf(
        binding.genderMale, binding.genderFemale
    )

    private val locationBtn = arrayOf(
        binding.actualLocationYes, binding.actualLocationNo
    )

    private fun validateImportantFields() {
        if (!fieldsEdited) {
            viewModel.showErrorSnackbar(binding.root.context.getString(R.string.change_some))
            return
        }
        var hasEmptyField = false
        hasEmptyField = validateFields(hasEmptyField)
        hasEmptyField = validateGender(hasEmptyField)
        hasEmptyField = validateMarital(hasEmptyField)
        hasEmptyField = validateActualLocation(hasEmptyField)

        if (hasEmptyField) {
            viewModel.onFailValidate()
        } else {
            if (binding.acceptBtn.isActive) {
                validateAndProvideValues()
                viewModel.onSuccessValidate()
            }
        }
    }

    private fun setCustomConditionsError() {
        binding.phoneNumberEt.setCustomConditionError {
            binding.phoneNumberEt.text.length == 16
        }

        binding.innEt.setCustomConditionError {
            binding.innEt.text.length == 14
        }

        binding.numberDocEt.setCustomConditionError {
            binding.numberDocEt.text.length == 7
        }
    }

    fun initValidateListeners() {
        setCustomConditionsError()
        initActualAddress()
        initFieldsListener()
        initOtherFieldsListener()
        initSelectableBtnListener(genderBtn, "GENDER")
        initSelectableBtnListener(maritalBtn, "MARITAL")
        initAcceptBtn()
    }

    private fun initAcceptBtn() {
        binding.acceptBtn.setOnClickListener {
            validateImportantFields()
        }
    }

    private fun validateFields(hasEmptyField: Boolean): Boolean {
        var result = hasEmptyField
        for (field in importantFields) {
            val fieldStatus = field.ifEmptyError()
            fieldStatus.ifTrue {
                result.ifFalse {
                    result = fieldStatus
                }
            }
        }
        return result
    }

    private fun initOtherFieldsListener() {
        for (field in otherFields) {
            field.onTextChanged {
                fieldsEdited = true
            }
        }
    }

    private fun initSelectableBtnListener(array: Array<SelectableButton>, key: String) {
        for (btn in array) {
            btn.setResultListener {
                fieldsEdited = true
                if (btn.isActive) {
                    checkedFields.add(key)
                } else {
                    checkedFields.remove(key)
                }
                checkAcceptBtn()
            }
        }
    }

    private fun initFieldsListener() {
        for (field in importantFields) {
            field.onTextChanged {
                fieldsEdited = true
                if (field.isSuccessFilled) {
                    checkedFields.add(rec.getResourceName(field.id))
                } else {
                    checkedFields.remove(rec.getResourceName(field.id))
                }
                checkAcceptBtn()
            }
        }
    }

    private fun initActualAddress() {
        viewModel.isActualLocation?.let {
            it.ifFalse {
                importantFields.addAll(
                    arrayOf(
                        binding.actualCountryEt, binding.actualAreaEt,
                        binding.actualRegionEt, binding.actualHouseEt
                    )
                )
            }
        }
    }

    fun checkAcceptBtn() {
        val importantSize = importantFields.size + 2
        if (fieldsEdited)
            binding.acceptBtn.isActive = checkedFields.size == importantSize
        else
            binding.acceptBtn.isActive = false
    }


    private fun validateActualLocation(hasEmptyField: Boolean): Boolean {
        var result = hasEmptyField
        val actualLocationIsNull = viewModel.isActualLocation.isNull()
        result.ifFalse { result = actualLocationIsNull }
        actualLocationIsNull.ifTrue {
            binding.actualLocationYes.setBackgroundResource(R.drawable.bg_rounded_error)
            binding.actualLocationNo.setBackgroundResource(R.drawable.bg_rounded_error)
        }
        return result
    }

    private fun validateGender(hasEmptyField: Boolean): Boolean {
        var result = hasEmptyField
        val genderIsNull = viewModel.gender.isNull()
        result.ifFalse { result = genderIsNull }
        binding.genderMale.error = genderIsNull
        binding.genderFemale.error = genderIsNull
        return result
    }

    private fun validateMarital(hasEmptyField: Boolean): Boolean {
        var result = hasEmptyField
        val maritalIsNull = viewModel.maritalStatus.isNull()
        result.ifFalse { result = maritalIsNull }
        binding.marriedBtn.error = maritalIsNull
        binding.singleBtn.error = maritalIsNull
        binding.widowerBtn.error = maritalIsNull
        binding.divorcedBtn.error = maritalIsNull
        return result
    }

    private fun validateAndProvideValues() = with(binding) {
        viewModel.apply {
            name = nameEt.textOrNull()
            lastname = lastNameEt.textOrNull()
            surname = surnameEt.textOrNull()
            phoneNumber = phoneNumberEt.textOrNull()
            phoneNumberMore = phoneNumberMoreEt.textOrNull()
            phoneNumberComfort = phoneNumberComfortEt.textOrNull()
            INN = innEt.textOrNull()
            docNumber = numberDocEt.textOrNull()
            docIssueNumber = issueNumberDocEt.textOrNull()
            village = villageEt.textOrNull()
            street = streetEt.textOrNull()
            house = houseEt.textOrNull()
            apartment = apartmentEt.textOrNull()
            jobCompany = jobCompanyEt.textOrNull()
            jobName = jobEt.textOrNull()
            jobAddress = jobAddressEt.textOrNull()
            actualVillage = actualVillageEt.textOrNull()
            actualStreet = actualStreetEt.textOrNull()
            actualHouse = actualHouseEt.textOrNull()
            actualApartment = actualApartmentEt.textOrNull()
            placeOfBirth = placeOfBirthEt.textOrNull()
        }
    }
}