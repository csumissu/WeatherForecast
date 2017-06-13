package csumissu.weatherforecast.model.local

import csumissu.weatherforecast.model.ForecastList
import csumissu.weatherforecast.model.ForecastDataStore
import io.reactivex.Flowable
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 07/06/2017
 */
@Singleton
class ForecastLocalProvider : ForecastDataStore {

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> {
        return Flowable.empty()
    }

    override fun saveDailyForecasts(latitude: Double, longitude: Double, forecastList: ForecastList) {
    }

}