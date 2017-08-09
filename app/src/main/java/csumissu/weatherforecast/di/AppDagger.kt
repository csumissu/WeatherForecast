package csumissu.weatherforecast.di

import android.app.Application
import android.content.Context
import csumissu.weatherforecast.App
import csumissu.weatherforecast.model.ForecastDataSource
import csumissu.weatherforecast.model.ForecastDataStore
import csumissu.weatherforecast.model.local.ForecastLocalProvider
import csumissu.weatherforecast.model.remote.ForecastApi
import csumissu.weatherforecast.model.remote.ForecastRemoteProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 07/06/2017
 */
@Module
class AppModule {

    @Provides
    @Singleton
    @ForApplication
    fun provideAppContext(app: Application): Context {
        return app.applicationContext
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
    fun provideForecastLocalProvider(@ForApplication context: Context): ForecastDataStore {
        return ForecastLocalProvider(context)
    }

}

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        MainUiModule::class
))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

}