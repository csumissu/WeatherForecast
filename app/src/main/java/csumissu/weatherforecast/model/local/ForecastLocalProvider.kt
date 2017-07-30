package csumissu.weatherforecast.model.local

import android.content.Context
import csumissu.weatherforecast.common.ForApplication
import csumissu.weatherforecast.model.ForecastDataStore
import csumissu.weatherforecast.model.ForecastList
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
        info("loading local daily forecasts")
        val location = DayForecastsEntity.getLocation(latitude, longitude)
        val entity = Realm.getDefaultInstance()
                .where(DayForecastsEntity::class.java)
                .equalTo("location", location)
                .greaterThan("lastUpdateMills", System.currentTimeMillis() - REFRESH_INTERVAL)
                .findFirst()
        info("loadDailyForecasts hitCache=${entity != null}")

        return if (entity == null) Flowable.empty<ForecastList>() else Flowable.just(entity.toBean())
    }

    override fun saveDailyForecasts(latitude: Double, longitude: Double, forecastList: ForecastList) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val entity = forecastList.toEntity(latitude, longitude)
        realm.insertOrUpdate(entity)
        realm.commitTransaction()
    }

    companion object {
        val REFRESH_INTERVAL = 30 * 1000
    }

}