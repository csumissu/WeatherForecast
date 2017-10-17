package csumissu.weatherforecast.model.local

import android.content.Context
import csumissu.weatherforecast.di.ForApplication
import csumissu.weatherforecast.model.Forecast
import csumissu.weatherforecast.model.ForecastDataStore
import csumissu.weatherforecast.model.ForecastList
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Maybe
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
        return Flowable.create(object : RealmOnSubscribe<ForecastList>() {
            override fun get(realm: Realm): ForecastList? {
                val entity = getDayForecastsEntity(realm, latitude, longitude)

                info("loadDailyForecasts hitCache=${entity != null}")
                return entity?.toBean()
            }

        }, BackpressureStrategy.MISSING)
    }

    override fun loadForecast(latitude: Double, longitude: Double, index: Int): Maybe<Forecast> {
        return Maybe.create(object : RealmOnSubscribe<Forecast>() {
            override fun get(realm: Realm): Forecast? {
                val entity = getDayForecastsEntity(realm, latitude, longitude)
                val forecast = entity?.toBean()?.get(index)

                if (forecast == null) {
                    throw IllegalArgumentException("Can not find any forecast by index $index")
                } else {
                    return forecast
                }
            }
        })
    }

    override fun saveDailyForecasts(latitude: Double, longitude: Double, forecastList: ForecastList) {
        Flowable.create(object : RealmOnSubscribe<Unit>() {
            override fun get(realm: Realm) {
                realm.insertOrUpdate(forecastList.toEntity(latitude, longitude))
            }
        }, BackpressureStrategy.BUFFER).subscribe()
    }

    private fun getDayForecastsEntity(realm: Realm, latitude: Double, longitude: Double): DayForecastsEntity? {
        return realm.where(DayForecastsEntity::class.java)
                .equalTo("location", DayForecastsEntity.getLocation(latitude, longitude))
                .greaterThan("lastUpdateMills", System.currentTimeMillis() - REFRESH_INTERVAL)
                .findFirst()
    }

    companion object {
        val REFRESH_INTERVAL = 10 * 60 * 1000
    }

}