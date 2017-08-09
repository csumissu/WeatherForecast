package csumissu.weatherforecast.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import csumissu.weatherforecast.viewmodel.ForecastsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 07/08/2017
 */
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ForecastsViewModel::class)
    abstract fun bindForecastListViewModel(forecastListViewMode: ForecastsViewModel): ViewModel

}

@Singleton
class ViewModelFactory
@Inject constructor(private val mCreators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = mCreators[modelClass]
        if (creator == null) {
            for ((key, value) in mCreators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class " + modelClass)
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}