package csumissu.weatherforecast.common

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.support.v4.app.Fragment
import org.jetbrains.anko.AnkoLogger

/**
 * @author yxsun
 * @since 07/06/2017
 */
abstract class BaseFragment : Fragment(), LifecycleRegistryOwner, AnkoLogger {

    private val mRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    override fun getLifecycle(): LifecycleRegistry {
        return mRegistry
    }

}