package pro.breez.bfsut.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pro.breez.bfsut.BuildConfig.BASE_URL
import pro.breez.data.cache.DataPreference
import pro.breez.data.repository.AddFarmerRepositoryImpl
import pro.breez.data.repository.AuthRepositoryImpl
import pro.breez.data.repository.LogsRepositoryImpl
import pro.breez.data.repository.MainRepositoryImpl
import pro.breez.data.rest.RestClient
import pro.breez.data.rest.RestClientImpl
import pro.breez.data.utility.adapter.TokenAuthenticator
import pro.breez.domain.repository.AddFarmerRepository
import pro.breez.domain.repository.AuthRepository
import pro.breez.domain.repository.LogsRepository
import pro.breez.domain.repository.MainRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideRestClient(
        dataPref: DataPreference,
        tokenAuthenticator: TokenAuthenticator,
    ): RestClient {
        return RestClientImpl(dataPref, BASE_URL, tokenAuthenticator)
    }

    @Provides
    fun provideAuthRepository(
        restClient: RestClient,
        dataPref: DataPreference
    ): AuthRepository {
        return AuthRepositoryImpl(restClient, dataPref)
    }

    @Provides
    fun provideMainRepository(
        restClient: RestClient,
        dataPref: DataPreference
    ): MainRepository {
        return MainRepositoryImpl(restClient, dataPref)
    }

    @Provides
    fun provideLogsRepository(
        restClient: RestClient,
        dataPref: DataPreference
    ): LogsRepository {
        return LogsRepositoryImpl(restClient, dataPref)
    }

    @Provides
    fun provideAddFarmerRepository(
        restClient: RestClient,
        dataPref: DataPreference
    ): AddFarmerRepository {
        return AddFarmerRepositoryImpl(restClient, dataPref)
    }

}