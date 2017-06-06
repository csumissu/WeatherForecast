package csumissu.weatherforecast

import android.Manifest
import android.arch.lifecycle.Observer
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.widget.Toolbar
import csumissu.weatherforecast.common.BaseActivity
import csumissu.weatherforecast.common.ToolbarManager
import csumissu.weatherforecast.util.LocationLiveData
import csumissu.weatherforecast.util.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.info

/**
 * @author yxsun
 * @since 01/06/2017
 */
class MainActivity : BaseActivity(), ToolbarManager {

    override val mToolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)
    }

    override fun onStart() {
        super.onStart()
        if (!PermissionUtils.requirePermissions(this, PERMISSIONS, REQUEST_PERMISSIONS_CODE)) {
            observeLocationChanges()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (permissions.size == grantResults.size) {
                observeLocationChanges()
            } else {
                finish()
            }
        }
    }

    private fun observeLocationChanges() {
        LocationLiveData.getInstance(this).observe(this, Observer {
            coordinate ->
            info("new data ${coordinate?.latitude} ${coordinate?.longitude}")
            if (coordinate != null) {
                val results = Geocoder(this@MainActivity).getFromLocation(coordinate.latitude, coordinate.longitude, 1)
                if (results != null && results.isNotEmpty()) {
                    val address = results[0]
                    mAddressView.text = "${address.locality} / ${address.countryName}"
                }
            }
        })
    }

    companion object {
        private val REQUEST_PERMISSIONS_CODE = 111
        private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

}

