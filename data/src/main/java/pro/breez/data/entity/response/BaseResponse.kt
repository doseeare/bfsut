package pro.breez.data.entity.response

data class BaseResponse<T>(
    val success: Boolean,
    val status: Int,
    val data: T
)