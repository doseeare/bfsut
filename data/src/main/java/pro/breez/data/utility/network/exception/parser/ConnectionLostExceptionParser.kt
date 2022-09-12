package pro.breez.data.utility.network.exception.parser

import pro.breez.domain.exception.ConnectionLostException
import java.net.ConnectException
import java.net.UnknownHostException

fun parseConnectionLostException(
    throwable: Throwable
): Throwable {

    return if (throwable is UnknownHostException || throwable is ConnectException) {
        ConnectionLostException()
    } else {
        throwable
    }
}