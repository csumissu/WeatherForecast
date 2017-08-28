package csumissu.weatherforecast.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import csumissu.weatherforecast.di.ForApplication
import csumissu.weatherforecast.extensions.locationManager
import csumissu.weatherforecast.model.Coordinate
import io.reactivex.Maybe
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author yxsun
 * @since 01/06/2017
 */
@Singleton
class LocationLiveData
@Inject constructor(@ForApplication context: Context,
                    private val mSchedulerProvider: BaseSchedulerProvider)
    : LiveData<Coordinate>(), AnkoLogger {

    private val mGeocoder = Geocoder(context)
    private val mLocationManager = context.locationManager

    private val mListener = object : SimpleLocationListener() {
        override fun onLocationChanged(location: Location?) {
            info("onLocationChanged() $location")
            updateCurrentLocation(location)
        }
    }

    private val mAddress = Transformations.switchMap(this) { coordinate ->
        val result = MutableLiveData<Address>()
        getFromLocation(coordinate)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe { result.value = it }
        result
    }

    override fun onActive() {
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500F, mListener)
    }

    override fun onInactive() {
        mLocationManager.removeUpdates(mListener)
    }

    fun getAddress(): LiveData<Address> {
        return mAddress
    }

    private fun updateCurrentLocation(location: Location?) {
        val coordinate = Coordinate(location?.latitude, location?.longitude)
        if (value != coordinate) {
            value = coordinate
        }
    }

    private fun getFromLocation(coordinate: Coordinate?): Maybe<Address> {
        info("getFromLocation() $coordinate")
        return Maybe.create {
            try {
                val results = if (coordinate == null) null else {
                    mGeocoder.getFromLocation(coordinate.latitude, coordinate.longitude, 1)
                }
                if (results != null && results.isNotEmpty()) {
                    it.onSuccess(results[0])
                }
            } catch (e: IOException) {
                error("Get address from location failed", e)
            }
            it.onComplete()
        }
    }

}