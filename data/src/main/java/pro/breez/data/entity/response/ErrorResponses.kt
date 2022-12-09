package pro.breez.data.entity.response

data class ErrorsResponse(
    val code: String,
    val field_errors: List<FieldError>,
    val message: String
)

data class FieldError(
    val field_name: String,
    val message: String
)

data class ErrorResponse(
    val code: String,
    val message: String,
)
