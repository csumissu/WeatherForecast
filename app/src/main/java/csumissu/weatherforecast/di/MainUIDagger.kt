package csumissu.weatherforecast.di

import csumissu.weatherforecast.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author yxsun
 * *
 * @since 09/08/2017
 */
@Module
abstract class MainUIModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}
