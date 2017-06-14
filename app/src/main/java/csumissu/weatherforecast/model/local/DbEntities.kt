package csumissu.weatherforecast.model.local

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * @author yxsun
 * @since 13/06/2017
 */
open class TemperatureEntity(
        var day: Float = 0F,
        var min: Float = 0F,
        var max: Float = 0F
) : RealmObject()

open class WeatherEntity(
        var description: String = "",
        var iconUrl: String = ""

) : RealmObject()

open class ForecastEntity(
        var date: Long = 0,
        var temperature: TemperatureEntity? = null,
        var weathers: RealmList<WeatherEntity> = RealmList(),
        var humidity: Int = 0,
        var pressure: Float = 0F,
        var windSpeed: Float = 0F,
        var windDegrees: Int = 0,
        var clouds: Int = 0
) : RealmObject()

open class CityEntity(
        @PrimaryKey
        var id: Long = 0,
        var name: String = "",
        var country: String = ""
) : RealmObject()

open class DayForecastsEntity(
        @Index
        var location: String = "",
        var city: CityEntity? = null,
        var forecasts: RealmList<ForecastEntity> = RealmList()
) : RealmObject() {
    companion object {
        fun getLocation(latitude: Double, longitude: Double): String {
            return "$latitude $longitude"
        }
    }
}