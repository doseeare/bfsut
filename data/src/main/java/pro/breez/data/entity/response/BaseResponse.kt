package pro.breez.data.entity.response

/**
 * Created by azamat on 7/6/21.
 */

data class BaseResponse<T>(
    val success: Boolean,
    val status: Int,
    val data: T
)