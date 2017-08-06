package csumissu.weatherforecast

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import csumissu.weatherforecast.common.BasePermissionsActivity
import csumissu.weatherforecast.common.ToolbarManager
import csumissu.weatherforecast.extensions.*
import csumissu.weatherforecast.ui.details.DetailsFragment
import csumissu.weatherforecast.ui.forecasts.ForecastsFragment
import csumissu.weatherforecast.model.Forecast
import csumissu.weatherforecast.util.LocationLiveData
import csumissu.weatherforecast.viewmodel.AddressViewModel
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import java.text.DateFormat

/**
 * @author yxsun
 * @since 01/06/2017
 */
class MainActivity : BasePermissionsActivity(), ToolbarManager {

    override val mToolbar by lazy { find<Toolbar>(R.id.toolbar) }
    private lateinit var mAddressViewModel: AddressViewModel
    private var mLocalityPref by DelegatesExt.preference<String>(this, PREF_LOCALITY)
    private var mCountryPref by DelegatesExt.preference<String>(this, PREF_COUNTRY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)

        if (savedInstanceState == null) {
            showFragment(ForecastsFragment(), R.id.mContentView, ForecastsFragment.TAG_NAME)
        }

        mAddressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        mAddressViewModel.getAddress().observe(this, Observer { it ->
            mLocalityPref = "${it?.locality}"
            mCountryPref = "${it?.countryName}"
            showActionBarTitle()
        })
    }

    override fun requiredPermissions(): Array<String> {
        return arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onPermissionsResult(acquired: Boolean) {
        if (acquired) observeLocationChanges() else finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        disableHomeAsUp()
        showActionBarTitle()
        supportActionBar?.subtitle = null
    }

    fun showDetails(forecast: Forecast) {
        enableHomeAsUp { onBackPressed() }
        supportActionBar?.title = mLocalityPref
        supportActionBar?.subtitle = forecast.date.toDateString(DateFormat.FULL)

        val detailsFragment = DetailsFragment.forForecast(forecast)
        showFragmentInTx(detailsFragment, R.id.mContentView)
    }

    private fun observeLocationChanges() {
        LocationLiveData.getInstance(this).observe(this, Observer { it ->
            info("new data ${it?.latitude} ${it?.longitude}")
            if (it != null) {
                val fragment = findFragmentByTag<ForecastsFragment>(ForecastsFragment.TAG_NAME)
                fragment?.updateCoordinate(it)

                mAddressViewModel.setCoordinate(it)
            }
        })
    }

    private fun showActionBarTitle() {
        if (mLocalityPref.isNotBlank() && mCountryPref.isNotBlank()) {
            supportActionBar?.title = "$mLocalityPref / $mCountryPref"
        } else {
            supportActionBar?.title = getString(R.string.app_name)
        }
    }

    companion object {
        val PREF_LOCALITY = "locality"
        val PREF_COUNTRY = "country"
    }

}

