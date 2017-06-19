package csumissu.weatherforecast

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.IdlingResource.ResourceCallback
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author yxsun
 * @since 19/06/2017
 */
class SimpleIdlingResource : IdlingResource {

    @Volatile
    private var mResourceCallback: ResourceCallback? = null
    private val mIsIdleNow = AtomicBoolean(true)

    fun setIdleNow(idleNow: Boolean) {
        mIsIdleNow.set(idleNow)
        if (idleNow) {
            mResourceCallback?.onTransitionToIdle()
        }
    }

    override fun getName(): String {
        return "Simple idling resource"
    }

    override fun isIdleNow(): Boolean {
        return mIsIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        mResourceCallback = callback
    }

}