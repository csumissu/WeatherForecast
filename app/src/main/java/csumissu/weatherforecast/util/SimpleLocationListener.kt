package csumissu.weatherforecast.util

import android.location.LocationListener
import android.os.Bundle

/**
 * @author yxsun
 * @since 02/06/2017
 */
abstract class SimpleLocationListener : LocationListener {

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }
}