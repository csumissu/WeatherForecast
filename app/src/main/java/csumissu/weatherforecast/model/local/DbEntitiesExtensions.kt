package csumissu.weatherforecast.model.local

import csumissu.weatherforecast.model.*
import io.realm.RealmList

/**
 * @author yxsun
 * @since 21/07/2017
 */
fun Temperature.toEntity(): TemperatureEntity {
    return TemperatureEntity(this.day, this.min, this.max)
}

fun TemperatureEntity?.toBean(): Temperature {
    val entity: TemperatureEntity
    if (this == null) {
        entity = TemperatureEntity()
    } else {
        entity = this
    }
    return Temperature(entity.day, entity.min, entity.max)
}

fun Weather.toEntity(): WeatherEntity {
    return WeatherEntity(this.description, this.iconCode)
}

fun WeatherEntity.toBean(): Weather {
    return Weather(this.description, this.iconCode)
}

@JvmName("weatherBeansToEntities")
fun List<Weather>.toEntityList(): RealmList<WeatherEntity> {
    val entities = RealmList<WeatherEntity>()
    this.mapNotNullTo(entities) { it.toEntity() }
    return entities
}

@JvmName("weatherEntitiesToBeans")
fun RealmList<WeatherEntity>.toBeanList(): List<Weather> {
    return this.mapNotNull { it.toBean() }
}

fun Forecast.toEntity(): ForecastEntity {
    return ForecastEntity(this.dt, this.temperature.toEntity(), this.weathers.toEntityList(),
            this.humidity, this.pressure, this.windSpeed, this.windDegrees, this.clouds)
}

fun ForecastEntity.toBean(): Forecast {
    return Forecast(this.date, this.temperature.toBean(), this.weathers.toBeanList(),
            this.humidity, this.pressure, this.windSpeed, this.windDegrees, this.clouds)
}

@JvmName("forecastBeansToEntities")
fun List<Forecast>.toEntityList(): RealmList<ForecastEntity> {
    val entities = RealmList<ForecastEntity>()
    this.mapNotNullTo(entities) { it.toEntity() }
    return entities
}

@JvmName("forecastEntitiesToBeans")
fun RealmList<ForecastEntity>.toBeanList(): List<Forecast> {
    return this.mapNotNull { it.toBean() }
}

fun City.toEntity(): CityEntity {
    return CityEntity(this.id, this.name, this.country)
}

fun CityEntity?.toBean(): City {
    val entity: CityEntity
    if (this == null) {
        entity = CityEntity()
    } else {
        entity = this
    }
    return City(entity.id, entity.name, entity.country)
}

fun ForecastList.toEntity(latitude: Double, longitude: Double): DayForecastsEntity {
    return DayForecastsEntity(DayForecastsEntity.getLocation(latitude, longitude),
            this.city.toEntity(), this.dailyForecast.toEntityList())
}

fun DayForecastsEntity.toBean(): ForecastList {
    return ForecastList(this.city.toBean(), this.forecasts.toBeanList())
}