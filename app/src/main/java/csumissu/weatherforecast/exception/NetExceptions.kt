package csumissu.weatherforecast.exception

import android.support.annotation.StringRes

/**
 * @author yxsun
 * @since 15/08/2017
 */
class ResponseException : Exception {

    constructor(cause: Throwable) : super(cause)

    constructor(message: String, cause: Throwable) : super(message, cause)

    var displayMessage: Int = 0
        @StringRes get() = field
        set(@StringRes value) {
            field = value
        }

    var errorCode: ErrorCode = ErrorCode.UNKNOWN
}

enum class ErrorCode {
    UNKNOWN,
    PARSE_ERROR,
    NETWORK_ERROR,
    HTTP_ERROR,
    SSL_ERROR,
    HOST_ERROR,
    API_ERROR,
    INNER_ERROR,
}