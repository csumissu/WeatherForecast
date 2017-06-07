package csumissu.weatherforecast.model.remote

import csumissu.weatherforecast.data.ForecastList
import csumissu.weatherforecast.model.ForecastDataSource
import io.reactivex.Flowable
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 06/06/2017
 */
@Singleton
class ForecastRemoteProvider(forecastApi: ForecastApi) : ForecastDataSource {

    val mForecastApi = forecastApi

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> {
        return mForecastApi.getDailyForecasts(latitude, longitude)
    }

}