package csumissu.weatherforecast.model

import csumissu.weatherforecast.data.ForecastList
import io.reactivex.Flowable

/**
 * @author yxsun
 * @since 06/06/2017
 */
interface ForecastDataSource {

    fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList>

}

interface ForecastDataStore : ForecastDataSource {

    fun saveDailyForecasts(latitude: Double, longitude: Double, forecastList: ForecastList)

}