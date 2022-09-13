package pro.breez.data.entity.response

data class ErrorsResponse(
    val success: Boolean,
    val status: Int,
    val data: ArrayList<ErrorBody>
)

data class ErrorResponse(
    val success: Boolean,
    val status: Int,
    val data: ErrorBody
)

data class ErrorBody(
    val message:String
)
