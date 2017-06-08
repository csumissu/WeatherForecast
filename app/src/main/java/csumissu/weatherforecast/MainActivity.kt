package csumissu.weatherforecast

import android.Manifest
import android.arch.lifecycle.Observer
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.widget.Toolbar
import csumissu.weatherforecast.common.BasePermissionsActivity
import csumissu.weatherforecast.common.ToolbarManager
import csumissu.weatherforecast.data.Forecast
import csumissu.weatherforecast.ui.DetailsFragment
import csumissu.weatherforecast.ui.ForecastsFragment
import csumissu.weatherforecast.util.ActivityUtils
import csumissu.weatherforecast.util.LocationLiveData
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.info

/**
 * @author yxsun
 * @since 01/06/2017
 */
class MainActivity : BasePermissionsActivity(), ToolbarManager {

    override val mToolbar by lazy { find<Toolbar>(R.id.toolbar) }
    private var mIsDetailsPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)
        if (savedInstanceState == null) {
            ActivityUtils.showFragment(supportFragmentManager, ForecastsFragment(), R.id.mContentView)
        }
    }

    override fun requiredPermissions(): Array<String> {
        return arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onPermissionsResult(acquired: Boolean) {
        if (acquired) observeLocationChanges() else finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mIsDetailsPage = false
    }

    fun showDetails(forecast: Forecast) {
        mIsDetailsPage = true
        val detailsFragment = DetailsFragment.forForecast(forecast)
        ActivityUtils.showFragmentInTx(supportFragmentManager, detailsFragment, R.id.mContentView)
    }

    private fun observeLocationChanges() {
        LocationLiveData.getInstance(this).observe(this, Observer { it ->
            info("new data ${it?.latitude} ${it?.longitude}")
            if (it != null) {
                val results = Geocoder(this).getFromLocation(it.latitude, it.longitude, 1)
                if (results != null && results.isNotEmpty()) {
                    val address = results[0]
                    mAddressView.text = "${address.locality} / ${address.countryName}"
                }
            }
        })
    }

}

