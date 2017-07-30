package csumissu.weatherforecast.model.remote

import csumissu.weatherforecast.model.ForecastDataSource
import csumissu.weatherforecast.model.ForecastList
import io.reactivex.Flowable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 06/06/2017
 */
@Singleton
class ForecastRemoteProvider
@Inject constructor(private val mForecastApi: ForecastApi)
    : ForecastDataSource, AnkoLogger {

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> {
        info("loading remote daily forecasts")
        return mForecastApi.getDailyForecasts(latitude, longitude)
    }

}