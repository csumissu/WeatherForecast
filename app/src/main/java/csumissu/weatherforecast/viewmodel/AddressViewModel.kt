package csumissu.weatherforecast.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.location.Address
import android.location.Geocoder
import csumissu.weatherforecast.model.Coordinate
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.error
import org.jetbrains.anko.uiThread
import java.io.IOException

/**
 * @author yxsun
 * @since 12/06/2017
 */
class AddressViewModel(private val mApp: Application) : AndroidViewModel(mApp), AnkoLogger {

    private val mInput = MutableLiveData<Coordinate>()
    private val mAddress = Transformations.switchMap(mInput) { it ->
        val result = MutableLiveData<Address>()
        if (it != null) {
            doAsync {
                try {
                    val results = Geocoder(mApp).getFromLocation(it.latitude, it.longitude, 1)
                    if (results != null && results.isNotEmpty()) {
                        uiThread {
                            result.value = results[0]
                        }
                    }
                } catch (error: IOException) {
                    error("Get address from location failed", error)
                }
            }
        }
        result
    }

    fun setCoordinate(coordinate: Coordinate) {
        mInput.value = coordinate
    }

    fun getAddress(): LiveData<Address> {
        return mAddress
    }

}