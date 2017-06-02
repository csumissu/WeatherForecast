package csumissu.weatherforecast

import android.Manifest
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.Observer
import android.os.Bundle
import csumissu.weatherforecast.util.LocationLiveData
import csumissu.weatherforecast.util.PermissionUtils
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * @author yxsun
 * @since 01/06/2017
 */
class MainActivity : LifecycleActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        })
    }

    companion object {
        private val REQUEST_PERMISSIONS_CODE = 111
        private val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

}

