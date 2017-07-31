package csumissu.weatherforecast.model.local

import android.content.Context
import csumissu.weatherforecast.common.ForApplication
import csumissu.weatherforecast.model.ForecastDataStore
import csumissu.weatherforecast.model.ForecastList
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.realm.Realm
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 07/06/2017
 */
@Singleton
class ForecastLocalProvider
@Inject constructor(@ForApplication private val context: Context)
    : ForecastDataStore, AnkoLogger {

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> {
        return Flowable.create({
            val location = DayForecastsEntity.getLocation(latitude, longitude)
            val entity = Realm.getDefaultInstance()
                    .where(DayForecastsEntity::class.java)
                    .equalTo("location", location)
                    .greaterThan("lastUpdateMills", System.currentTimeMillis() - REFRESH_INTERVAL)
                    .findFirst()
            info("loadDailyForecasts hitCache=${entity != null}")

            if (entity != null) {
                it.onNext(entity.toBean())
            }

            it.onComplete()
        }, BackpressureStrategy.MISSING)
    }

    override fun saveDailyForecasts(latitude: Double, longitude: Double, forecastList: ForecastList) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.insertOrUpdate(forecastList.toEntity(latitude, longitude))
        realm.commitTransaction()
    }

    companion object {
        val REFRESH_INTERVAL = 30 * 1000
    }

}