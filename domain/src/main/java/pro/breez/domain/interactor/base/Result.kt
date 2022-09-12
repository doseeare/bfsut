package pro.breez.domain.interactor.base

sealed class Result<out T> {
    class Success<T>(val element: T) : Result<T>() {
        override fun <E> map(transform: (T) -> E): Result<E> {
            val result = Success(transform(element))
            result.networkConnectionEnable = networkConnectionEnable
            return result
        }

        override fun <E> flatMap(transform: (T) -> Result<E>): Result<E> {
            val result = transform(element)
            result.networkConnectionEnable = networkConnectionEnable
            return result
        }

        override fun completedMap(transform: (T) -> Unit): CompletableResult {
            transform(element)
            return CompletableResult.Success()
        }

        override fun onError(action: (Throwable) -> Throwable): Result<T> {
            return this
        }

        override fun out(): T? {
            return element
        }
    }

    class Exception(val throwable: Throwable) : Result<Nothing>() {
        override fun <E> map(transform: (Nothing) -> E): Result<E> {
            return this
        }

        override fun <E> flatMap(transform: (Nothing) -> Result<E>): Result<E> {
            return this
        }

        override fun completedMap(transform: (Nothing) -> Unit): CompletableResult {
            return CompletableResult.Exception(throwable)
        }

        override fun onError(action: (Throwable) -> Throwable): Result<Nothing> {
            return Exception(action(throwable))
        }

        override fun out(): Nothing? {
            return null
        }
    }

    var networkConnectionEnable = false

    abstract fun onError(action: (Throwable) -> Throwable): Result<T>

    abstract fun <E> map(transform: (T) -> E): Result<E>

    abstract fun <E> flatMap(transform: (T) -> Result<E>): Result<E>

    abstract fun completedMap(transform: (T) -> Unit): CompletableResult

    abstract fun out(): T?

    fun performOnSuccess(onSuccess: (T) -> Unit) {
        if (this is Success) {
            onSuccess(this.element)
        }
    }

    fun perform(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) {
        when (this) {
            is Success -> onSuccess(element)
            is Exception -> onError(throwable)
        }
    }
}
