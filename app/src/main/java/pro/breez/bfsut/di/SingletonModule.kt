package pro.breez.bfsut.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pro.breez.bfsut.BuildConfig.BASE_URL
import pro.breez.data.cache.DataPreference
import pro.breez.data.repository.AuthRepositoryImpl
import pro.breez.data.repository.MainRepositoryImpl
import pro.breez.data.rest.RestClient
import pro.breez.data.rest.RestClientImpl
import pro.breez.domain.repository.AuthRepository
import pro.breez.domain.repository.MainRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideRestClient(
        dataPref: DataPreference
    ): RestClient {
        return RestClientImpl(dataPref, BASE_URL)
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

}