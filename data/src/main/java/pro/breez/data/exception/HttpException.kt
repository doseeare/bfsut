package pro.breez.data.exception

/**
 * Created by azamat on 9/6/21.
 */

class HttpException(val code: Int, message: String) : Exception(message)