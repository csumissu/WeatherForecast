package csumissu.weatherforecast

import android.app.Application
import com.crashlytics.android.Crashlytics
import csumissu.weatherforecast.dagger.AppComponent
import csumissu.weatherforecast.dagger.AppModule
import csumissu.weatherforecast.dagger.DaggerAppComponent
import csumissu.weatherforecast.extensions.DelegatesExt
import io.fabric.sdk.android.Fabric
import io.realm.Realm

/**
 * @author yxsun
 * @since 07/06/2017
 */
class App : Application() {

    private val mAppComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Realm.init(this)
        Fabric.with(this, Crashlytics())
    }

    fun component() = mAppComponent

    companion object {
        var INSTANCE: App by DelegatesExt.notNullSingleValue()
    }

}