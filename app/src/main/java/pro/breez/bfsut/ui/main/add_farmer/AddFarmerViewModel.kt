package pro.breez.bfsut.ui.main.add_farmer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.breez.bfsut.base.BaseViewModel
import pro.breez.bfsut.model.GenderEnum
import pro.breez.bfsut.model.MaritalStatusEnum
import pro.breez.bfsut.util.DateUtil
import pro.breez.bfsut.util.alert.dialog.SelectorDialogBuilderImpl
import pro.breez.domain.interactor.*
import pro.breez.domain.model.output.MfSysModel
import javax.inject.Inject

@HiltViewModel
class AddFarmerViewModel @Inject constructor(
    private val nationalityUseCase: NationalityUseCase,
    private val docTypesUseCase: DocTypesUseCase,
    private val docIssueUseCase: DocIssueUseCase,
    private val areaUseCase: AreaUseCase,
    private val countryUseCase: CountryUseCase,
    private val regionUseCase: RegionUseCase,
    private val educationUseCase: EducationUseCase
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

    //Поля field
    var name: String? = null
    var lastname: String? = null
    var surname: String? = null
    var phoneNumber: String? = null
    var phoneNumberMore: String? = null
    var phoneNumberComfort: String? = null
    var INN: String? = null
    var docNumber: String? = null
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
    var isActualLocation: Boolean? = null

    //Поля с SelectableButton
    var maritalStatus: MaritalStatusEnum? = null
    var gender: GenderEnum? = null

    fun birthDayClicked() {
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
            birthdayLV.postValue(DateUtil.toDate(it))
        }
        showDialogFragment.postValue(dateRangePicker)
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
            MfSysModel(0, "0", "Иностранец", ""),
            MfSysModel(1, "1", "Резидент", "")
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

    fun maritalSelected(status: MaritalStatusEnum) {
        maritalStatus = status
    }

    fun genderSelected(gender: GenderEnum) {
        this.gender = gender
    }

    fun initAcceptClicked() {
        if (checkImportantFields()) {
            showErrorSnackbar("Заполните все обязательные поля")
            return
        }

    }

    private fun checkImportantFields(): Boolean {
        val fields = arrayOf(
            name, lastname, birthdayLV.value,
            nationalityLV.value?.name, citizenLV.value?.name,
            gender?.name, phoneNumber,
            INN, docTypesLV.value?.name,
            docSeriesLV.value?.name, docNumber,
            docIssueLV.value?.name, docWhenLV.value,
            countryLV.value?.name, areaLv.value?.name,
            regionLV.value?.name,
            jobName, jobCompany, jobAddress
        )
        for (f in fields) {
            if (f == null) {
                return true
            }
        }
        return false
    }

}