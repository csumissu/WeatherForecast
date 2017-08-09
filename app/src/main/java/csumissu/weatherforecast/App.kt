package csumissu.weatherforecast

import android.app.Activity
import android.app.Application
import csumissu.weatherforecast.di.AutoInjector
import csumissu.weatherforecast.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import javax.inject.Inject

/**
 * @author yxsun
 * @since 07/06/2017
 */
class App : Application(), HasActivityInjector {

    @Inject
    lateinit var mDispatchingAndroidInject: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        initAppComponent()
        AutoInjector.init(this)
    }

    override fun activityInjector() = mDispatchingAndroidInject

    private fun initAppComponent() {
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

}