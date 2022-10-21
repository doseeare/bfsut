package pro.breez.bfsut.util.validator

import pro.breez.bfsut.R
import pro.breez.bfsut.databinding.FragmentAddFarmerBinding
import pro.breez.bfsut.ui.main.add_farmer.AddFarmerViewModel
import pro.breez.bfsut.util.ifFalse
import pro.breez.bfsut.util.ifTrue
import pro.breez.bfsut.util.isNull

class AddFarmerFieldValidator(
    val binding: FragmentAddFarmerBinding,
    val viewModel: AddFarmerViewModel
) {
    private val importantFields = arrayListOf(
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

    fun validateImportantFields() {
        var hasEmptyField = false
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
        setCustomConditionsError()
        hasEmptyField = validateFields(hasEmptyField)
        hasEmptyField = validateGender(hasEmptyField)
        hasEmptyField = validateMarital(hasEmptyField)
        hasEmptyField = validateActualLocation(hasEmptyField)

        if (hasEmptyField) {
            viewModel.onFailValidate()
            return
        } else {
            validateAndProvideValues()
            viewModel.onSuccessValidate()
            return
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
            surname = lastNameEt.textOrNull()
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