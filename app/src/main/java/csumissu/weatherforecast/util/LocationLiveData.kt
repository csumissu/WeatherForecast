package csumissu.weatherforecast.util

import android.arch.lifecycle.LiveData
import android.content.Context
import android.location.Location
import android.location.LocationManager
import csumissu.weatherforecast.data.Coordinate


/**
 * @author yxsun
 * @since 01/06/2017
 */
class LocationLiveData private constructor(context: Context) : LiveData<Coordinate>() {

    private val mLocationManager = context.getSystemService(
            Context.LOCATION_SERVICE) as LocationManager

    private val mListener = object : SimpleLocationListener() {
        override fun onLocationChanged(location: Location?) {
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
        if (value?.latitude != coordinate.latitude || value?.longitude != coordinate.longitude) {
            value = coordinate
        }
    }

    companion object {
        private var instance: LocationLiveData? = null

        fun getInstance(ctx: Context): LocationLiveData {
            if (instance == null) {
                instance = LocationLiveData(ctx.applicationContext)
            }
            return instance!!
        }
    }
}