package csumissu.weatherforecast.common

import csumissu.weatherforecast.extensions.checkGrantResults
import csumissu.weatherforecast.extensions.requirePermissions


/**
 * @author yxsun
 * @since 08/06/2017
 */
abstract class BasePermissionsActivity : BaseActivity() {

    abstract fun requiredPermissions(): Array<String>

    abstract fun onPermissionsResult(acquired: Boolean)

    override fun onStart() {
        super.onStart()
        if (!requirePermissions(requiredPermissions(), REQUEST_PERMISSIONS_CODE)) {
            onPermissionsResult(true)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            onPermissionsResult(checkGrantResults(grantResults))
        }
    }

    companion object {
        private val REQUEST_PERMISSIONS_CODE = 111
    }

}