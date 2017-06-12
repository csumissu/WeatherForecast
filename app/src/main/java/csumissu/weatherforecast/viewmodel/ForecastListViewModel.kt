package csumissu.weatherforecast.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import csumissu.weatherforecast.App
import csumissu.weatherforecast.data.Coordinate
import csumissu.weatherforecast.data.ForecastList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author yxsun
 * @since 11/06/2017
 */
class ForecastListViewModel(app: Application) : AndroidViewModel(app) {

    private val mApp = app as App
    private val mInput = MutableLiveData<Coordinate>()
    private val mForecasts = Transformations.switchMap(mInput) { it ->
        val data = MutableLiveData<ForecastList>()
        mApp.component().getForecastRepository()
                .loadDailyForecasts(it.latitude, it.longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data.value = it }
        data
    }

    fun setCoordinate(coordinate: Coordinate) {
        mInput.value = coordinate
    }

    fun getDailyForecasts(): LiveData<ForecastList> {
        return mForecasts
    }

}