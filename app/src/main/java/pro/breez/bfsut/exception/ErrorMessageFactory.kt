package pro.breez.bfsut.exception

import pro.breez.domain.exception.ConnectionLostException


class ErrorMessageFactory {

    companion object {
        fun create(throwable: Throwable): String? {

            if (throwable is ConnectionLostException) {
                return "Проверь подключение к интернету"
            }

            return throwable.localizedMessage
        }
    }
}