package csumissu.weatherforecast.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import csumissu.weatherforecast.model.Coordinate
import csumissu.weatherforecast.model.ForecastList
import csumissu.weatherforecast.model.ForecastRepository
import csumissu.weatherforecast.util.BaseSchedulerProvider
import javax.inject.Inject

/**
 * @author yxsun
 * @since 11/06/2017
 */
class ForecastsViewModel
@Inject constructor(forecastRepository: ForecastRepository,
                    private val mSchedulerProvider: BaseSchedulerProvider)
    : ViewModel() {

    private val mLocation = MutableLiveData<Coordinate>()
    private val mForecasts = Transformations.switchMap(mLocation) { it ->
        val data = MutableLiveData<ForecastList>()
        forecastRepository
                .loadDailyForecasts(it.latitude, it.longitude)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe { data.value = it }
        data
    }

    fun getDailyForecasts(): LiveData<ForecastList> = mForecasts

    fun getCoordinate(): Coordinate? = mLocation.value

    fun setCoordinate(coordinate: Coordinate) {
        mLocation.value = coordinate
    }

}