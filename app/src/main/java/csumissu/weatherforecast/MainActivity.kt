package csumissu.weatherforecast

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import csumissu.weatherforecast.common.BasePermissionsActivity
import csumissu.weatherforecast.common.ToolbarManager
import csumissu.weatherforecast.extensions.*
import csumissu.weatherforecast.model.Forecast
import csumissu.weatherforecast.ui.details.DetailsFragment
import csumissu.weatherforecast.ui.forecasts.ForecastsFragment
import csumissu.weatherforecast.viewmodel.LocationLiveData
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import org.jetbrains.anko.find
import org.jetbrains.anko.info
import java.text.DateFormat
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
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var mLocationLiveData: LocationLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)

        if (savedInstanceState == null) {
            showFragment(ForecastsFragment(), R.id.mContentView, TAG_FORECASTS)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            when (supportFragmentManager.backStackEntryCount) {
                0 -> {
                    disableHomeAsUp()
                    showActionBarTitle()
                    supportActionBar?.subtitle = null
                }
            }
        }
    }

    override fun requiredPermissions(): Array<String> {
        return arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    override fun onPermissionsResult(acquired: Boolean) {
        if (acquired) observeLocationChanges() else finish()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return mAndroidInjector
    }

    fun showDetails(forecast: Forecast) {
        enableHomeAsUp { onBackPressed() }
        supportActionBar?.title = mLocalityPref
        supportActionBar?.subtitle = forecast.date.toDateString(DateFormat.FULL)

        val detailsFragment = DetailsFragment.forForecast(forecast)
        showFragmentInTx(detailsFragment, R.id.mContentView, TAG_DETAILS)
    }

    private fun observeLocationChanges() {
        mLocationLiveData.observe(this, Observer {
            info("new data ${it?.latitude} ${it?.longitude}")
            if (it != null) {
                val fragment = findFragmentByTag<ForecastsFragment>(TAG_FORECASTS)
                fragment?.updateCoordinate(it)
            }
        })
        mLocationLiveData.getAddress().observe(this, Observer {
            mLocalityPref = "${it?.locality}"
            mCountryPref = "${it?.countryName}"
            showActionBarTitle()
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
        val TAG_FORECASTS = "tag_forecasts"
        val TAG_DETAILS = "tag_details"
    }

}

