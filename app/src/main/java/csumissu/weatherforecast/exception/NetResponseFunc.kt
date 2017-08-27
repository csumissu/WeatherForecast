package csumissu.weatherforecast.exception

import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import csumissu.weatherforecast.util.JsonUtils
import io.reactivex.Flowable
import io.reactivex.functions.Function
import org.json.JSONException
import org.reactivestreams.Publisher
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

/**
 * @author yxsun
 * @since 15/08/2017
 */
class NetResponseFunc<T> : Function<Throwable, Publisher<out T>> {
    override fun apply(t: Throwable): Publisher<out T> =
            Flowable.error<T>(ExceptionEngine.handleException(t))
}

private object ExceptionEngine {

    private val BAD_REQUEST = 400
    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404
    private val REQUEST_TIMEOUT = 408
    private val INTERNAL_SERVER_ERROR = 500
    private val BAD_GATEWAY = 502
    private val SERVICE_UNAVAILABLE = 503
    private val GATEWAY_TIMEOUT = 504

    fun handleException(e: Throwable): ResponseException {
        var ex = ResponseException(e)
        if (e is HttpException) {
            val content = e.response().errorBody()?.string()
            val response = JsonUtils.convertFromJson<CommonResponse>(content)

            if (response is CommonResponse && response.isFailed()) {
                ex = ResponseException(response.message, e)
                ex.errorCode = ErrorCode.API_ERROR
            } else {
                ex.errorCode = ErrorCode.HTTP_ERROR
            }

            ex.displayMessage = when (e.code()) {
                BAD_REQUEST -> 0
                UNAUTHORIZED -> 0
                FORBIDDEN -> 0
                NOT_FOUND -> 0
                REQUEST_TIMEOUT -> 0
                GATEWAY_TIMEOUT -> 0
                INTERNAL_SERVER_ERROR -> 0
                BAD_GATEWAY -> 0
                SERVICE_UNAVAILABLE -> 0
                else -> 0
            }
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            ex.errorCode = ErrorCode.PARSE_ERROR
            ex.displayMessage = 0
        } else if (e is ConnectException) {
            ex.errorCode = ErrorCode.NETWORK_ERROR
            ex.displayMessage = 0
        } else if (e is SSLHandshakeException) {
            ex.errorCode = ErrorCode.SSL_ERROR
            ex.displayMessage = 0
        } else if (e is UnknownHostException) {
            ex.errorCode = ErrorCode.HOST_ERROR
            ex.displayMessage = 0
        } else {
            ex.errorCode = ErrorCode.UNKNOWN
            ex.displayMessage = 0
        }
        return ex
    }

}

private class CommonResponse
constructor(@SerializedName("cod") val code: String,
            val message: String) {

    fun isFailed(): Boolean = code != "200"

}