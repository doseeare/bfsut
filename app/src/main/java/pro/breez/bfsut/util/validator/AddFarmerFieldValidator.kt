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
        binding.areaEt, binding.regionEt, binding.houseEt,
        binding.educationEt, binding.jobEt,
        binding.jobCompanyEt, binding.jobAddressEt
    )

    fun validateImportant(): Boolean {
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
        for (field in importantFields) {
            val fieldStatus = field.ifEmptyError()
            fieldStatus.ifTrue {
                hasEmptyField.ifFalse {
                    hasEmptyField = fieldStatus
                }
            }
        }
        hasEmptyField = validateGender(hasEmptyField)
        hasEmptyField = validateMarital(hasEmptyField)
        hasEmptyField = validateActualLocation(hasEmptyField)
        return if (hasEmptyField) {
            viewModel.showErrorSnackbar("Заполните все обязательные поля")
            false
        } else {
            validateAndProvide()
            true
        }
    }

    private fun validateActualLocation(hasEmptyField: Boolean): Boolean {
        var hasEmptyField1 = hasEmptyField
        val actualLocationIsNull = viewModel.isActualLocation.isNull()
        hasEmptyField1.ifFalse { hasEmptyField1 = actualLocationIsNull }
        actualLocationIsNull.ifTrue {
            binding.actualLocationYes.setBackgroundResource(R.drawable.bg_rounded_error)
            binding.actualLocationNo.setBackgroundResource(R.drawable.bg_rounded_error)
        }
        return hasEmptyField1
    }

    private fun validateGender(hasEmptyField: Boolean): Boolean {
        var hasEmptyField1 = hasEmptyField
        val genderIsNull = viewModel.gender.isNull()
        hasEmptyField1.ifFalse { hasEmptyField1 = genderIsNull }
        binding.genderMale.error = genderIsNull
        binding.genderFemale.error = genderIsNull
        return hasEmptyField1
    }

    private fun validateMarital(hasEmptyField: Boolean): Boolean {
        var hasEmptyField1 = hasEmptyField
        val maritalIsNull = viewModel.maritalStatus.isNull()
        hasEmptyField1.ifFalse { hasEmptyField1 = maritalIsNull }
        binding.marriedBtn.error = maritalIsNull
        binding.singleBtn.error = maritalIsNull
        binding.widowerBtn.error = maritalIsNull
        binding.divorcedBtn.error = maritalIsNull
        return hasEmptyField1
    }

    private fun validateAndProvide() = with(binding) {
        viewModel.apply {
            name = nameEt.text
            lastname = lastNameEt.text
            surname = lastNameEt.text
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
        }
    }
}