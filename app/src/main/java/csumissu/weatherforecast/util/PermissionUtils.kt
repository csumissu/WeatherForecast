package csumissu.weatherforecast.util

import android.app.Activity
import android.content.Context
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker

/**
 * @author yxsun
 * @since 02/06/2017
 */
object PermissionUtils {

    fun requirePermissions(activity: Activity, permissionArray: Array<String>, requestCode: Int): Boolean {
        val permissions = permissionArray.filterNot { isPermissionGranted(activity, it) }
        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), requestCode)
        }
        return permissions.isNotEmpty()
    }

    fun isPermissionGranted(ctx: Context, permission: String): Boolean {
        return PermissionChecker.checkSelfPermission(ctx, permission) == PermissionChecker.PERMISSION_GRANTED
    }

}