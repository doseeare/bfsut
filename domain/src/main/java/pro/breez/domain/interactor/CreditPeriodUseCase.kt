package pro.breez.domain.interactor

import pro.breez.domain.interactor.base.Result
import pro.breez.domain.interactor.base.UseCase
import pro.breez.domain.model.input.PeriodBody
import pro.breez.domain.model.output.PeriodModel
import pro.breez.domain.repository.MainRepository
import javax.inject.Inject

class CreditPeriodUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : UseCase<ArrayList<PeriodModel>, PeriodBody>() {
    override suspend fun doOnBackground(params: PeriodBody?): Result<ArrayList<PeriodModel>> {
        val map = HashMap<String, String>()
        val arrayOfPeriod = arrayListOf<PeriodModel>()
        map["amount"] = params!!.amount
        map["product_bank_id"] = params.productId
        return mainRepository.getPeriod(map).map {
            for (i in it.period_min..it.period_max) {
                arrayOfPeriod.add(PeriodModel(period = "$i месяцев", inDigit = i))
            }
            arrayOfPeriod
        }
    }

}