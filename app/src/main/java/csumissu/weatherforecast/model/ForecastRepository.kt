package csumissu.weatherforecast.model

import csumissu.weatherforecast.common.Local
import csumissu.weatherforecast.common.Remote
import csumissu.weatherforecast.data.ForecastList
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 07/06/2017
 */
@Singleton
class ForecastRepository @Inject constructor(@Remote remoteProvider: ForecastDataSource,
                                             @Local localProvider: ForecastDataStore) : ForecastDataSource {
    private val mRemoteProvider = remoteProvider
    private val mLocalProvider = localProvider

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> {
        val localObservable = mLocalProvider.loadDailyForecasts(latitude, longitude)
        val remoteObservable = mRemoteProvider.loadDailyForecasts(latitude, longitude)
                .doOnNext { mLocalProvider.saveDailyForecasts(latitude, longitude, it) }
        return Flowable.concat(localObservable, remoteObservable).firstElement().toFlowable()
    }

}