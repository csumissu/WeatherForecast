package csumissu.weatherforecast.extensions

import java.text.DateFormat

/**
 * @author yxsun
 * @since 13/06/2017
 */
fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    return DateFormat.getDateInstance(dateFormat).format(this)
}