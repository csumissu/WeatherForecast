package csumissu.weatherforecast.extensions

import android.content.Context
import android.location.LocationManager
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author yxsun
 * @since 05/06/2017
 */
fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager

fun Context.inflate(@LayoutRes res: Int, parent: ViewGroup? = null, attach: Boolean = false): View {
    return LayoutInflater.from(this).inflate(res, parent, attach)
}