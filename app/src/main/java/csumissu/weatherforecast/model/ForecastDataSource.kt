package csumissu.weatherforecast.model

import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * @author yxsun
 * @since 06/06/2017
 */
interface ForecastDataSource {

    fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList>

    fun loadForecast(latitude: Double, longitude: Double, index: Int): Maybe<Forecast>

}

interface ForecastDataStore : ForecastDataSource {

    fun saveDailyForecasts(latitude: Double, longitude: Double, forecastList: ForecastList)

}