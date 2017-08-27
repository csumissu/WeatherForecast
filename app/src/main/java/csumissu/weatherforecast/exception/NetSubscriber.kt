package csumissu.weatherforecast.exception

import csumissu.weatherforecast.common.BaseContract
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author yxsun
 * @since 20/08/2017
 */
abstract class NetSubscriber<T>(private val baseView: BaseContract.IView? = null) : Subscriber<T> {

    override fun onSubscribe(s: Subscription) {
        baseView?.showProgress()
        s.request(Long.MAX_VALUE)
    }

    override fun onComplete() {
        baseView?.hideProgress()
    }

    override fun onError(t: Throwable) {
        baseView?.hideProgress()

        val error = transformError(t)
        error.printStackTrace()

        onFailed(error)
    }

    protected abstract fun onFailed(error: ResponseException)

    private fun transformError(t: Throwable): ResponseException {
        return if (t is ResponseException) {
            t
        } else {
            val error = ResponseException(t)
            error.errorCode = ErrorCode.INNER_ERROR
            error.displayMessage = 0
            error
        }
    }
}