package csumissu.weatherforecast

import android.Manifest
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import csumissu.weatherforecast.common.BasePermissionsActivity
import csumissu.weatherforecast.common.ToolbarManager
import csumissu.weatherforecast.extensions.DelegatesExt
import csumissu.weatherforecast.extensions.findFragmentByTag
import csumissu.weatherforecast.extensions.showFragment
import csumissu.weatherforecast.ui.forecasts.ForecastsFragment
import csumissu.weatherforecast.util.LocationLiveData
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * @author yxsun
 * @since 01/06/2017
 */
class MainActivity : BasePermissionsActivity(), ToolbarManager, HasSupportFragmentInjector {

    override val mToolbar by lazy { find<Toolbar>(R.id.toolbar) }

    private var mLocalityPref by DelegatesExt.preference<String>(this, PREF_LOCALITY)
    private var mCountryPref by DelegatesExt.preference<String>(this, PREF_COUNTRY)

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var mLocationLiveData: LocationLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)

        if (savedInstanceState == null) {
            showFragment(ForecastsFragment(), R.id.mContentView, TAG_FORECASTS)
        }
    }

    override fun supportFragmentInjector() = mAndroidInjector

    override fun requiredPermissions() = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onPermissionsResult(acquired: Boolean) {
        if (acquired) observeLocationChanges() else finish()
    }

    private fun observeLocationChanges() {
        mLocationLiveData.observe(this, Observer { coordinate ->
            coordinate?.let {
                info("new data ${it.latitude} ${it.longitude}")
                val fragment = findFragmentByTag<ForecastsFragment>(TAG_FORECASTS)
                fragment?.updateCoordinate(it)
            }
        })
        mLocationLiveData.getAddress().observe(this, Observer { address ->
            address?.let {
                mLocalityPref = it.locality
                mCountryPref = it.countryName
                supportActionBar?.title = "$mLocalityPref / $mCountryPref"
            }
        })
    }

    companion object {
        val PREF_LOCALITY = "locality"
        val PREF_COUNTRY = "country"

        val TAG_FORECASTS = "tag_forecasts"
    }

}

