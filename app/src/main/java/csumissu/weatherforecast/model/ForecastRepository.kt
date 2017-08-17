package csumissu.weatherforecast.model

import csumissu.weatherforecast.di.Local
import csumissu.weatherforecast.di.Remote
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 07/06/2017
 */
@Singleton
class ForecastRepository
@Inject constructor(@Remote private val mRemoteProvider: ForecastDataSource,
                    @Local private val mLocalProvider: ForecastDataStore)
    : ForecastDataSource, AnkoLogger {

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> {
        info("loadDailyForecasts($latitude, $longitude)")
        val localObservable = mLocalProvider.loadDailyForecasts(latitude, longitude)
        val remoteObservable = mRemoteProvider.loadDailyForecasts(latitude, longitude)
                .doOnNext {
                    mLocalProvider.saveDailyForecasts(latitude, longitude, it)
                }
        return Flowable.concat(localObservable, remoteObservable)
                .firstOrError()
                .toFlowable()
    }

    override fun loadForecast(latitude: Double, longitude: Double, index: Int): Maybe<Forecast> =
            mLocalProvider.loadForecast(latitude, longitude, index)

}