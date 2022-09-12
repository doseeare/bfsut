package pro.breez.domain.interactor.base

import kotlinx.coroutines.*
import pro.breez.domain.exception.ConnectionLostException

abstract class UseCase<T, Params> {
    abstract suspend fun doOnBackground(params: Params?): Result<T>

    private var isDisposed = false

    fun execute(scope: CoroutineScope, params: Params? = null, onResult: (Result<T>) -> Unit) {
        isDisposed = false
        scope.launch(Dispatchers.Main) {
            try {
                val deferred = async(Dispatchers.IO) {
                    try {
                        val result = doOnBackground(params)
                        if (result is Result.Exception && result.throwable is ConnectionLostException && scope.isActive) {
                            execute(scope, params, onResult)
                        }
                        result
                    } catch (e:Throwable) {
                        Result.Exception(e)
                    }
                }
                returnResult(onResult, deferred.await())
            } catch (t:Throwable) {
                t.printStackTrace()
                returnResult(onResult, Result.Exception(t))
            }
        }
    }

    private fun returnResult(onResult: (Result<T>) -> Unit, result: Result<T>) {
        if (isDisposed) {
            return
        }
        onResult(result)
    }

    fun dispose() {
        isDisposed = true
    }
}