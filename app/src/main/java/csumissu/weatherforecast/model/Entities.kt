package csumissu.weatherforecast.model

import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

/**
 * @author yxsun
 * @since 02/06/2017
 */
data class Coordinate(private val _latitude: Double? = 34.275, private val _longitude: Double? = 108.953) {
    private val format = DecimalFormat("#.###")

    val latitude: Double
        get() = format.format(_latitude).toDouble()

    val longitude: Double
        get() = format.format(_longitude).toDouble()

}

data class Temperature(val day: Float, val min: Float, val max: Float)

data class Weather(val description: String,
                   @SerializedName("icon") val iconCode: String) {
    val iconUrl: String
        get() = "http://openweathermap.org/img/w/$iconCode.png"
}

data class Forecast(val dt: Long,
                    @SerializedName("temp") val temperature: Temperature,
                    @SerializedName("weather") val weathers: List<Weather>,
                    val humidity: Int,
                    val pressure: Float,
                    val windSpeed: Float,
                    @SerializedName("deg") val windDegrees: Int,
                    val clouds: Int) {
    val date: Long
        get() = dt * 1000
}

data class City(val id: Long, val name: String, val country: String)

data class ForecastList(val city: City, @SerializedName("list") val dailyForecast: List<Forecast>) {

    val size: Int
        get() = dailyForecast.size

    operator fun get(position: Int) = dailyForecast[position]

}