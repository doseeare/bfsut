package pro.breez.domain.repository

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.output.MfSysModel

interface AddFarmerRepository {
    fun getNationality(): Result<List<MfSysModel>>
    fun getDocType(): Result<List<MfSysModel>>
    fun getDocIssue(): Result<List<MfSysModel>>
    fun getAreas(countryId : String): Result<List<MfSysModel>>
    fun getCountry() : Result<List<MfSysModel>>
    fun getRegions(id : String) : Result<List<MfSysModel>>
    fun getEducations() : Result<List<MfSysModel>>
}