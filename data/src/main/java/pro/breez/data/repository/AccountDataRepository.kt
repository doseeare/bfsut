/*
package pro.breez.data.repository

import pro.breez.data.auth.wrapper.AuthWrapper
import pro.breez.data.cache.factory.SharedPreferencesFactory
import pro.breez.data.entity.AccountEntity
import pro.breez.data.persistence.room.executor.RoomExecutorsProvider
import pro.breez.data.rest.RestClient
import pro.breez.data.source.accounts.AccountsDataStoreFactory
import pro.breez.data.utility.app_manager.ApplicationInfoManager
import pro.breez.domain.interactor.CompletableResult
import pro.breez.domain.model.home.AccountModel
import pro.breez.domain.model.home.AccountsModel
import pro.breez.domain.repository.AccountRepository

class AccountDataRepository(
    private val restClient: RestClient,
    private val authWrapper: AuthWrapper,
    private val accountsDataStoreFactory: AccountsDataStoreFactory,
    private val preferencesFactory: SharedPreferencesFactory,
    private val accountEntityDataMapper: AccountEntityDataMapper,
    private val partnerSharedPreferences: PartnerSharedPreferences,
    private val tokenSharedPreferences: TokenSharedPreferences,
    private val roomExecutorsProvider: RoomExecutorsProvider,
    private val accountsEntityDataMapper: AccountsEntityDataMapper,
    private val applicationInfoManager: ApplicationInfoManager
) : AccountRepository {

    override fun fetchAccounts(): Result<AccountsModel> {
        return accountsDataStoreFactory.retrieveRemoteDataStore().getAccounts()
            .map { collection ->
                accountsDataStoreFactory.retrieveLocalDataStore().saveAccounts(collection)

                val finalCollection = collection.filter { it.currency != "BON" }

                val accounts = if (finalCollection.isNotEmpty()) {
                    val partnerLocalData = partnerSharedPreferences.partner
                    AccountsEntity(finalCollection.toMutableList().sortedBy { it.currency == partnerLocalData.currency }.reversed())
                } else {
                    AccountsEntity(listOf(AccountEntity("", 0, "", "")))
                }

                accounts.qrCode = preferencesFactory.createQrPrefs().qr
                accountsEntityDataMapper.transform(accounts)
            }
    }

    override fun getAccounts(): Result<AccountsModel> {
        return accountsDataStoreFactory.retrieveLocalDataStore().getAccounts()
            .map { collection ->
                val finalCollection = collection.filter { it.currency != "BON" }

                val accounts = if (finalCollection.isNotEmpty()) {
                    val partnerLocalData = partnerSharedPreferences.partner
                    AccountsEntity(finalCollection.toMutableList().sortedBy { it.currency == partnerLocalData.currency }.reversed())
                } else {
                    AccountsEntity(listOf(AccountEntity("", 0, "", "")))
                }

                accounts.qrCode = preferencesFactory.createQrPrefs().qr
                accountsEntityDataMapper.transform(accounts)
            }
    }

    override fun getCurrencyBalance(): Result<AccountModel> {
        val partnerLocalData = partnerSharedPreferences.partner

        return authWrapper.wrap { token ->
            restClient.getAccountApi().getAccounts(token, partnerLocalData.currency).map {
                accountEntityDataMapper.transform(it.data)
            }
        }
    }

    override fun getQrCode(): Result<String> {
        return authWrapper.wrap { token ->
            restClient.getAccountApi().getQrCode(token).map {
                preferencesFactory.createQrPrefs().qr = it.data.qr_code
                it.data.qr_code
            }
        }
    }

    override fun registerFirebaseToken(): CompletableResult {
        val fcmToken = tokenSharedPreferences.fcmToken ?: return CompletableResult.Exception(
            Throwable("FCM token is not proved")
        )

        val appVersion = applicationInfoManager.getAppVersion()
        val osVersion = android.os.Build.VERSION.RELEASE
        val deviceName = "${android.os.Build.MANUFACTURER}, ${android.os.Build.MODEL}"

        return roomExecutorsProvider.provideQueryExecutor().executeQuery {
            val users = it.userDao().get()
            if (users.isEmpty()) return@executeQuery
            val pushParamsEntity = PushParamsBody(users.first().mobile, fcmToken, appVersion, osVersion, deviceName)

            authWrapper.wrap { token ->
                val response = restClient.getAccountApi().registerFCMToken(token, pushParamsEntity)
                if (response is Result.Success && response.element.data.is_saved) {
                    return@wrap response
                } else {
                    return@wrap Result.Exception(Throwable("FCM token didn't saved"))
                }
            }
        }.completedMap {}
    }

    override fun clearAccounts(): CompletableResult {
        return accountsDataStoreFactory.retrieveLocalDataStore().clearAccounts()
    }
}*/
