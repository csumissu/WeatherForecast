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
        return Flowable.create(object : RealmOnSubscribe<ForecastList>() {

            override fun get(realm: Realm): ForecastList? {
                val entity = realm
                        .where(DayForecastsEntity::class.java)
                        .equalTo("location", DayForecastsEntity.getLocation(latitude, longitude))
                        .greaterThan("lastUpdateMills", System.currentTimeMillis() - REFRESH_INTERVAL)
                        .findFirst()
                info("loadDailyForecasts hitCache=${entity != null}")
                return entity?.toBean()
            }

        }, BackpressureStrategy.MISSING)
    }

    override fun saveDailyForecasts(latitude: Double, longitude: Double, forecastList: ForecastList) {
        Flowable.create(object : RealmOnSubscribe<Unit>() {
            override fun get(realm: Realm) {
                realm.insertOrUpdate(forecastList.toEntity(latitude, longitude))
            }
        }, BackpressureStrategy.BUFFER).subscribe()
    }

    companion object {
        val REFRESH_INTERVAL = 30 * 1000
    }

}