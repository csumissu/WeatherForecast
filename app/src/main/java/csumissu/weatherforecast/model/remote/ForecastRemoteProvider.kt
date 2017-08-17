package csumissu.weatherforecast.model.remote

import csumissu.weatherforecast.model.Forecast
import csumissu.weatherforecast.model.ForecastDataSource
import csumissu.weatherforecast.model.ForecastList
import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 06/06/2017
 */
@Singleton
class ForecastRemoteProvider
@Inject constructor(private val mForecastApi: ForecastApi)
    : ForecastDataSource {

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> =
            mForecastApi.getDailyForecasts(latitude, longitude)

    override fun loadForecast(latitude: Double, longitude: Double, index: Int): Maybe<Forecast> {
        throw UnsupportedOperationException("Not support loading forecast from network")
    }

}