package csumissu.weatherforecast.util

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @author yxsun
 * @since 06/08/2017
 */
abstract class SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

}