package csumissu.weatherforecast.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.location.Address
import android.location.Geocoder
import csumissu.weatherforecast.data.Coordinate

/**
 * @author yxsun
 * @since 12/06/2017
 */
class AddressViewModel(private val mApp: Application) : AndroidViewModel(mApp) {

    private val mInput = MutableLiveData<Coordinate>()
    private val mAddress = Transformations.switchMap(mInput) { it ->
        val result = MutableLiveData<Address>()
        if (it != null) {
            val results = Geocoder(mApp).getFromLocation(it.latitude, it.longitude, 1)
            if (results != null && results.isNotEmpty()) {
                result.value = results[0]
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