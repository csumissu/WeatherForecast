package csumissu.weatherforecast

import android.app.Application
import csumissu.weatherforecast.common.AppComponent
import csumissu.weatherforecast.common.AppModule
import csumissu.weatherforecast.common.DaggerAppComponent
import csumissu.weatherforecast.extensions.DelegatesExt
import io.realm.Realm

/**
 * @author yxsun
 * @since 07/06/2017
 */
class App : Application() {

    private lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Realm.init(this)
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun component() = mAppComponent

    companion object {
        var INSTANCE: App by DelegatesExt.notNullSingleValue()
    }

}