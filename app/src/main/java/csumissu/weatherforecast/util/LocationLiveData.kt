package csumissu.weatherforecast.util

import android.arch.lifecycle.LiveData
import android.content.Context
import android.location.Location
import csumissu.weatherforecast.di.ForApplication
import csumissu.weatherforecast.extensions.locationManager
import csumissu.weatherforecast.model.Coordinate
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author yxsun
 * @since 01/06/2017
 */
@Singleton
class LocationLiveData
@Inject constructor(@ForApplication context: Context)
    : LiveData<Coordinate>(), AnkoLogger {

    private val mLocationManager = context.locationManager

    private val mListener = object : SimpleLocationListener() {
        override fun onLocationChanged(location: Location?) {
            info("onLocationChanged() $location")
            updateCurrentLocation(location)
        }
    }

    override fun onActive() {
        for (provider in mLocationManager.allProviders) {
            mLocationManager.requestLocationUpdates(provider, 1000L, 500F, mListener)
        }
    }

    override fun onInactive() {
        mLocationManager.removeUpdates(mListener)
    }

    private fun updateCurrentLocation(location: Location?) {
        val coordinate = Coordinate(location?.latitude, location?.longitude)
        if (value != coordinate) {
            value = coordinate
        }
    }

}