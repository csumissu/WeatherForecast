package csumissu.weatherforecast.model.local

import android.content.Context
import csumissu.weatherforecast.extensions.DelegatesExt
import csumissu.weatherforecast.model.ForecastDataStore
import csumissu.weatherforecast.model.ForecastList
import io.reactivex.Flowable
import io.realm.Realm
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Singleton

/**
 * @author yxsun
 * @since 07/06/2017
 */
@Singleton
class ForecastLocalProvider(context: Context) : ForecastDataStore, AnkoLogger {

    private var mLastTimeMills: Long by DelegatesExt.preference(context, PREF_TIME_MILLS, 0)

    override fun loadDailyForecasts(latitude: Double, longitude: Double): Flowable<ForecastList> {
        if (isCacheInvalid()) {
            return Flowable.empty()
        }

        val location = DayForecastsEntity.getLocation(latitude, longitude)
        val entity = Realm.getDefaultInstance()
                .where(DayForecastsEntity::class.java)
                .equalTo("location", location)
                .findFirst()
        info("loadDailyForecasts hitCache=${entity != null}")
        return if (entity == null) Flowable.empty<ForecastList>() else Flowable.just(DayForecastsEntity.toEntity(entity))
    }

    override fun saveDailyForecasts(latitude: Double, longitude: Double, forecastList: ForecastList) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val entity = DayForecastsEntity.fromEntity(latitude, longitude, forecastList)
        realm.insertOrUpdate(entity)
        realm.commitTransaction()
    }

    private fun isCacheInvalid(): Boolean {
        val currentMills = System.currentTimeMillis()
        if (Math.abs(currentMills - mLastTimeMills) > REFRESH_INTERVAL) {
            mLastTimeMills = currentMills
            return true
        } else {
            return false
        }
    }

    companion object {
        val PREF_TIME_MILLS = "time_mills"
        val REFRESH_INTERVAL = 30 * 1000
    }

}