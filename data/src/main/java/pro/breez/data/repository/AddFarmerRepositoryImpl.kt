package pro.breez.data.repository

import pro.breez.data.cache.DataPreference
import pro.breez.data.rest.RestClient
import pro.breez.domain.interactor.base.Result
import pro.breez.domain.model.input.FarmerBody
import pro.breez.domain.model.output.MfSysModel
import pro.breez.domain.repository.AddFarmerRepository

class AddFarmerRepositoryImpl(
    private val restClient: RestClient,
    private val dataPreference: DataPreference
) : AddFarmerRepository {

    override fun getNationality(): Result<List<MfSysModel>> {
        return restClient.mainApi.getNationality(dataPreference.token)
    }

    override fun getDocType(): Result<List<MfSysModel>> {
        return restClient.mainApi.getDocTypes(dataPreference.token)
    }

    override fun getDocIssue(): Result<List<MfSysModel>> {
        return restClient.mainApi.getDocIssue(dataPreference.token)
    }

    override fun getAreas(countryId: String): Result<List<MfSysModel>> {
        return restClient.mainApi.getArea(dataPreference.token)
    }

    override fun getCountry(): Result<List<MfSysModel>> {
        return restClient.mainApi.getCountries(dataPreference.token)
    }

    override fun getRegions(id: String): Result<List<MfSysModel>> {
        return restClient.mainApi.getRegions(dataPreference.token, id)
    }

    override fun getEducations(): Result<List<MfSysModel>> {
        return restClient.mainApi.getEducations(dataPreference.token)
    }

    override fun getJobPurpose(): Result<List<MfSysModel>> {
        return restClient.mainApi.getJobPurpose(dataPreference.token)
    }

    override fun postFarmer(body: FarmerBody): Result<String> {
        return restClient.mainApi.postFarmer(dataPreference.token, body)
    }

}


