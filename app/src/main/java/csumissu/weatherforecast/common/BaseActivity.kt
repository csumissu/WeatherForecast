package csumissu.weatherforecast.common

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger

/**
 * @author yxsun
 * @since 06/06/2017
 */
abstract class BaseActivity : AppCompatActivity(), LifecycleRegistryOwner, AnkoLogger {

    protected lateinit var mRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRegistry = LifecycleRegistry(this)
    }

    override fun getLifecycle(): LifecycleRegistry {
        return mRegistry
    }

}