package csumissu.weatherforecast.di

import csumissu.weatherforecast.MainActivity
import csumissu.weatherforecast.ui.details.DetailsActivity
import csumissu.weatherforecast.ui.forecasts.ForecastsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author yxsun
 * @since 09/08/2017
 */
@Module
abstract class MainUiModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeDetailsActivity(): DetailsActivity

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeForecastsFragment(): ForecastsFragment

}
