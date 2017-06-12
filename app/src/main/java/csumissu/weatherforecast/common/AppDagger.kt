package csumissu.weatherforecast.common

import android.content.Context
import csumissu.weatherforecast.App
import csumissu.weatherforecast.model.ForecastDataSource
import csumissu.weatherforecast.model.ForecastDataStore
import csumissu.weatherforecast.model.ForecastRepository
import csumissu.weatherforecast.model.local.ForecastLocalProvider
import csumissu.weatherforecast.model.remote.ForecastApi
import csumissu.weatherforecast.model.remote.ForecastRemoteProvider
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 07/06/2017
 */
@Module
class AppModule(private val mApp: App) {

    @Provides
    @Singleton
    @ForApplication
    fun provideAppContext(): Context {
        return mApp
    }

    @Provides
    @Singleton
    @Remote
    fun provideForecastRemoteProvider(): ForecastDataSource {
        return ForecastRemoteProvider(ForecastApi.getApiService())
    }

    @Provides
    @Singleton
    @Local
    fun provideForecastLocalProvider(): ForecastDataStore {
        return ForecastLocalProvider()
    }

}

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(app: App)

    @ForApplication
    fun getAppContext(): Context

    fun getForecastRepository(): ForecastRepository

}