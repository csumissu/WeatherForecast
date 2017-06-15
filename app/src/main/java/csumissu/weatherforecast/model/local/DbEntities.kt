package csumissu.weatherforecast.model.local

import csumissu.weatherforecast.model.*
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
) : RealmObject() {
    companion object {
        fun fromEntity(temperature: Temperature) = TemperatureEntity(temperature.day, temperature.min, temperature.max)

        fun toEntity(entity: TemperatureEntity?): Temperature {
            val newValue: TemperatureEntity
            if (entity == null) {
                newValue = TemperatureEntity()
            } else {
                newValue = entity
            }
            return Temperature(newValue.day, newValue.min, newValue.max)
        }
    }
}

open class WeatherEntity(
        var description: String = "",
        var iconCode: String = ""

) : RealmObject() {
    companion object {
        fun fromEntity(weather: Weather) = WeatherEntity(weather.description, weather.iconCode)

        fun fromEntities(weathers: List<Weather>): RealmList<WeatherEntity> {
            val entities = RealmList<WeatherEntity>()
            weathers.mapTo(entities) { fromEntity(it) }
            return entities
        }

        fun toEntity(entity: WeatherEntity) = Weather(entity.description, entity.iconCode)

        fun toEntities(entities: RealmList<WeatherEntity>): List<Weather> {
            return entities.map { toEntity(it) }
        }
    }
}

open class ForecastEntity(
        var date: Long = 0,
        var temperature: TemperatureEntity? = null,
        var weathers: RealmList<WeatherEntity> = RealmList(),
        var humidity: Int = 0,
        var pressure: Float = 0F,
        var windSpeed: Float = 0F,
        var windDegrees: Int = 0,
        var clouds: Int = 0
) : RealmObject() {
    companion object {
        fun fromEntity(forecast: Forecast): ForecastEntity {
            return ForecastEntity(forecast.dt,
                    TemperatureEntity.fromEntity(forecast.temperature),
                    WeatherEntity.fromEntities(forecast.weathers),
                    forecast.humidity, forecast.pressure, forecast.windSpeed,
                    forecast.windDegrees, forecast.clouds)
        }

        fun fromEntities(forecasts: List<Forecast>): RealmList<ForecastEntity> {
            val entities = RealmList<ForecastEntity>()
            forecasts.mapTo(entities) { fromEntity(it) }
            return entities
        }

        fun toEntity(entity: ForecastEntity): Forecast {
            return Forecast(entity.date,
                    TemperatureEntity.toEntity(entity.temperature),
                    WeatherEntity.toEntities(entity.weathers),
                    entity.humidity, entity.pressure, entity.windSpeed,
                    entity.windDegrees, entity.clouds)
        }

        fun toEntities(entities: RealmList<ForecastEntity>): List<Forecast> {
            return entities.map { toEntity(it) }
        }
    }
}

open class CityEntity(
        @PrimaryKey
        var id: Long = 0,
        var name: String = "",
        var country: String = ""
) : RealmObject() {
    companion object {
        fun fromEntity(city: City) = CityEntity(city.id, city.name, city.country)

        fun toEntity(entity: CityEntity?): City {
            val newValue: CityEntity
            if (entity == null) {
                newValue = CityEntity()
            } else {
                newValue = entity
            }
            return City(newValue.id, newValue.name, newValue.country)
        }
    }
}

open class DayForecastsEntity(
        @Index
        var location: String = "",
        var city: CityEntity? = null,
        var forecasts: RealmList<ForecastEntity> = RealmList()
) : RealmObject() {
    companion object {
        fun fromEntity(latitude: Double, longitude: Double, forecastList: ForecastList): DayForecastsEntity {
            return DayForecastsEntity(getLocation(latitude, longitude),
                    CityEntity.fromEntity(forecastList.city),
                    ForecastEntity.fromEntities(forecastList.dailyForecast))
        }

        fun toEntity(entity: DayForecastsEntity): ForecastList {
            return ForecastList(CityEntity.toEntity(entity.city), ForecastEntity.toEntities(entity.forecasts))
        }

        fun getLocation(latitude: Double, longitude: Double): String {
            return "$latitude $longitude"
        }
    }
}