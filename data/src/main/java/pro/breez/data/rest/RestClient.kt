package pro.breez.data.rest

import pro.breez.data.rest.api.AuthApi
import pro.breez.data.rest.api.MainApi

interface RestClient {
    val authApi: AuthApi
    val mainApi: MainApi
}