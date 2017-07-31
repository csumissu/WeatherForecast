package csumissu.weatherforecast.model.remote

import csumissu.weatherforecast.model.ForecastDataSource
import csumissu.weatherforecast.model.ForecastList
import io.reactivex.Flowable
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

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> {
        return mForecastApi.getDailyForecasts(latitude, longitude)
    }

}