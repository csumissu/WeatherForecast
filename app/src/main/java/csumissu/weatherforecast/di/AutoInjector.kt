package csumissu.weatherforecast.di

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import csumissu.weatherforecast.App
import csumissu.weatherforecast.util.SimpleActivityLifecycleCallbacks
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * @author yxsun
 * @since 06/08/2017
 */
interface Injectable

object AutoInjector {

    fun init(app: App) {
        app.registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                handleActivityInjection(activity)
            }
        })
    }

    private fun handleActivityInjection(activity: Activity) {
        if (activity is Injectable) {
            AndroidInjection.inject(activity)
        } else if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)

            if (activity is FragmentActivity) {
                handleFragmentInjection(activity.supportFragmentManager)
            }
        }
    }

    private fun handleFragmentInjection(fragmentManager: FragmentManager) {
        fragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentAttached(fm: FragmentManager?, f: Fragment?, ctx: Context?) {
                        if (f is Injectable) {
                            AndroidSupportInjection.inject(f)
                        }
                    }
                }, true)
    }


}