package pro.breez.bfsut.ui.main.add_farmer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.R
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.GenderEnum
import pro.breez.bfsut.model.MaritalStatusEnum
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.alert.QuestionDialog
import pro.breez.bfsut.util.alert.dialog.AlertDialogBuilderImpl
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.*
import pro.breez.domain.model.input.FarmerBody
import pro.breez.domain.model.output.MfSysModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FarmerAddViewModel @Inject constructor(
    private val nationalityUseCase: NationalityUseCase,
    private val docTypesUseCase: DocTypesUseCase,
    private val docIssueUseCase: DocIssueUseCase,
    private val areaUseCase: AreaUseCase,
    private val countryUseCase: CountryUseCase,
    private val regionUseCase: RegionUseCase,
    private val educationUseCase: EducationUseCase,
    private val jobPurposeUseCase: JobPurposeUseCase,
    private val addFarmerUse: AddFarmerUseCase
) : BaseViewModel() {

    //Поля drop
    val birthdayLV = MutableLiveData<String>()
    val nationalityLV = MutableLiveData<MfSysModel>()
    val docTypesLV = MutableLiveData<MfSysModel>()
    val docIssueLV = MutableLiveData<MfSysModel>()
    val docSeriesLV = MutableLiveData<MfSysModel>()
    val citizenLV = MutableLiveData<MfSysModel>()
    val docWhenLV = MutableLiveData<String?>()
    val countryLV = MutableLiveData<MfSysModel>()
    val areaLv = MutableLiveData<MfSysModel>()
    val regionLV = MutableLiveData<MfSysModel>()
    val educationLV = MutableLiveData<MfSysModel>()
    val jobPurposeLV = MutableLiveData<MfSysModel>()

    //Поля field
    var name: String? = null
    var lastname: String? = null
    var surname: String? = null
    var phoneNumber: String? = null
    var phoneNumberMore: String? = null
    var phoneNumberComfort: String? = null
    var INN: String? = null
    var docNumber: String? = null
    var docIssueNumber: String? = null
    var village: String? = null
    var street: String? = null
    var house: String? = null
    var apartment: String? = null
    var jobCompany: String? = null
    var jobName: String? = null
    var jobAddress: String? = null

    //Поля для фактического адреса
    val actualCountryLV = MutableLiveData<MfSysModel>()
    val actualAreaLv = MutableLiveData<MfSysModel>()
    val actualRegionLV = MutableLiveData<MfSysModel>()
    var actualVillage: String? = null
    var actualStreet: String? = null
    var actualHouse: String? = null
    var actualApartment: String? = null
    var isActualLocation: Boolean? = null

    var placeOfBirth: String? = null

    //Поля с SelectableButton
    var maritalStatus: MaritalStatusEnum? = null
    var gender: GenderEnum? = null

    fun birthDayClicked() {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        startDate.add(Calendar.YEAR, -88)
        endDate.add(Calendar.YEAR, -18)

        val constraintsBuilder =
            CalendarConstraints.Builder()
        constraintsBuilder.setStart(startDate.timeInMillis)
        constraintsBuilder.setEnd(endDate.timeInMillis)

        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .setPositiveButtonText("Подвердить")
            .setCalendarConstraints(constraintsBuilder.build())
            .setNegativeButtonText("Отменить")
            .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            birthdayLV.postValue(DateUtil.toDate(it))
        }
        showDialogFragment.startEvent(dateRangePicker)
    }

    fun nationClicked() {
        nationalityUseCase.execute(viewModelScope) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<MfSysModel>()
                selector.setList(it)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(MfSysModel::name.name)
                selector.setResultListener {
                    nationalityLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun citizenClicked() {
        val list = listOf(
            MfSysModel(0, "0", "Гражданин КР", ""),
            MfSysModel(1, "1", "Не гражданин КР", "")
        )
        val selector = SelectorDialogBuilderImpl<MfSysModel>()
        selector.setList(list)
        selector.setVmScope(viewModelScope)
        selector.setSearchByVal(MfSysModel::name.name)
        selector.setResultListener {
            citizenLV.postValue(it)
        }
        showSelectorDialog.startEvent(selector)
    }

    fun docTypeClicked() {
        docTypesUseCase.execute(viewModelScope) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<MfSysModel>()
                selector.setList(it)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(MfSysModel::name.name)
                selector.setResultListener {
                    docTypesLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun docSeriesClicked() {
        val list = listOf(
            MfSysModel(0, "A", "A", ""),
            MfSysModel(1, "ID", "ID", "")
        )
        val selector = SelectorDialogBuilderImpl<MfSysModel>()
        selector.setList(list)
        selector.setVmScope(viewModelScope)
        selector.setSearchByVal(MfSysModel::name.name)
        selector.setResultListener {
            docSeriesLV.postValue(it)
        }
        showSelectorDialog.startEvent(selector)
    }

    fun docIssueClicked() {
        docIssueUseCase.execute(viewModelScope) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<MfSysModel>()
                selector.setList(it)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(MfSysModel::name.name)
                selector.setResultListener {
                    docIssueLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun whenDocClicked() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())

        val dateRangePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату")
            .setPositiveButtonText("Подвердить")
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(System.currentTimeMillis())
            .setNegativeButtonText("Отменить")
            .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            docWhenLV.postValue(DateUtil.toDate(it))
        }
        showDialogFragment.postValue(dateRangePicker)
    }

    fun countryClicked(isActual: Boolean) {
        countryUseCase.execute(viewModelScope) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<MfSysModel>()
                selector.setList(it)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(MfSysModel::name.name)
                selector.setResultListener {
                    if (isActual) actualCountryLV.postValue(it)
                    else countryLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun areaClicked(isActual: Boolean) {
        val country = if (isActual) actualCountryLV else countryLV
        if (country.value == null) return
        areaUseCase.execute(viewModelScope, country.value?.mfsys_id) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<MfSysModel>()
                selector.setList(it)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(MfSysModel::name.name)
                selector.setResultListener {
                    if (isActual) actualAreaLv.postValue(it)
                    else areaLv.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun regionClicked(isActual: Boolean) {
        val area = if (isActual) actualAreaLv else areaLv
        if (area.value == null) return
        regionUseCase.execute(viewModelScope, area.value?.mfsys_id) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<MfSysModel>()
                selector.setList(it)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(MfSysModel::name.name)
                selector.setResultListener {
                    if (isActual) actualRegionLV.postValue(it)
                    else regionLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }
    }

    fun educationClicked() {
        educationUseCase.execute(viewModelScope) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<MfSysModel>()
                selector.setList(it)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(MfSysModel::name.name)
                selector.setResultListener {
                    educationLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }

    }

    fun jobPurposeClicked() {
        jobPurposeUseCase.execute(viewModelScope) {
            handleResult(it) {
                val selector = SelectorDialogBuilderImpl<MfSysModel>()
                selector.setList(it)
                selector.setVmScope(viewModelScope)
                selector.setSearchByVal(MfSysModel::name.name)
                selector.setResultListener {
                    jobPurposeLV.postValue(it)
                }
                showSelectorDialog.startEvent(selector)
            }
        }

    }

    fun maritalSelected(status: MaritalStatusEnum) {
        maritalStatus = status
    }

    fun genderSelected(gender: GenderEnum) {
        this.gender = gender
    }

    fun onSuccessValidate() {
        val body = FarmerBody(
            first_name = name,
            father_name = lastname,
            last_name = surname,
            gender = gender?.key,
            document_series = docSeriesLV.value?.name,
            document_number = docNumber,
            document_date = DateUtil.reformatDate(docWhenLV.value),
            document_issue = getId(docIssueLV),
            document_issue_number = docIssueNumber,
            tax_number = INN,
            document_type = getId(docTypesLV),
            state = getId(countryLV),
            country = getId(areaLv),
            region = getId(regionLV),
            village = village,
            address = street,
            house = house,
            apartment = apartment,
            actual_state = getId(actualCountryLV),
            actual_country = getId(actualAreaLv),
            actual_region = getId(actualRegionLV),
            actual_village = actualVillage,
            actual_address = actualStreet,
            actual_house = actualHouse,
            actual_apartment = actualApartment,
            place_of_birth = placeOfBirth,
            is_actual_address_match = isActualLocation,
            phone_number = phoneNumber,
            phone_number_additional = phoneNumberMore,
            marital_status = maritalStatus?.key,
            spouse_customer_id = null,
            family_count = null,
            nationality = getId(nationalityLV),
            education = getId(educationLV),
            purpose = getId(jobPurposeLV),
            job = jobCompany,
            job_address = jobAddress,
            job_position = jobName,
            resident = getId(citizenLV),
            birth_date = DateUtil.reformatDate(birthdayLV.value)
        )
        addFarmerUse.execute(viewModelScope, body) {
            handleResult(it) {
                val dialog = AlertDialogBuilderImpl()
                dialog.setTitle("Фермер был создан")
                dialog.setSubTitle("Можете начать собирать молоко")
                dialog.setIcon(R.drawable.ic_success)
                dialog.setDismissListener {
                    popBackStack.startEvent(null)
                }
                showAlertDialog.startEvent(dialog)
            }
        }
    }

    private fun getId(lv: MutableLiveData<MfSysModel>): Int? {
        return lv.value?.id
    }


    fun onFailValidate() {
        showErrorSnackbar("Заполните все обязательные поля")
    }

    fun backBtnClicked() {
        val dialog = QuestionDialog().apply {
            setTitle("Хотите выйти,\nне сохранив данные?")
            onPositiveBtnClicked {
                popBackStack.startEvent(null)
            }
        }
        showDialogFragment.startEvent(dialog)
    }

}