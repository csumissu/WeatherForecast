package csumissu.weatherforecast.data

import java.text.DecimalFormat

/**
 * @author yxsun
 * @since 02/06/2017
 */
data class Coordinate(private val _latitude: Double? = 108.953, private val _longitude: Double? = 34.275) {
    private val format = DecimalFormat("#.###")

    val latitude: Double
        get() = format.format(_latitude).toDouble()

    val longitude: Double
        get() = format.format(_longitude).toDouble()

}