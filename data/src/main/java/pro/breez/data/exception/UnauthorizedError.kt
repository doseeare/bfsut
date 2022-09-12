package pro.breez.data.exception

import java.lang.Exception

class UnauthorizedError(val code: Int, message: String) : Exception(message)