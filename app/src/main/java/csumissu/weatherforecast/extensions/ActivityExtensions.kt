package csumissu.weatherforecast.extensions

import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * @author yxsun
 * @since 20/07/2017
 */
fun AppCompatActivity.showFragment(fragment: Fragment, @IdRes frameId: Int, tag: String? = null) {
    val transaction = supportFragmentManager.beginTransaction()
    if (!fragment.isAdded) {
        transaction.add(frameId, fragment, tag)
    }
    transaction.show(fragment).commit()
}

fun AppCompatActivity.hideFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().hide(fragment).commit()
}

fun AppCompatActivity.showFragmentInTx(fragment: Fragment, @IdRes frameId: Int, tag: String? = null) {
    supportFragmentManager.beginTransaction()
            .add(frameId, fragment, tag)
            .addToBackStack(tag)
            .commit()
}

inline fun <reified T> AppCompatActivity.findFragmentByTag(tag: String): T? {
    return supportFragmentManager.findFragmentByTag(tag) as T?
}

fun AppCompatActivity.requirePermissions(permissionArray: Array<String>, requestCode: Int): Boolean {
    val permissions = permissionArray.filterNot { isPermissionGranted(it) }
    if (permissions.isNotEmpty()) {
        ActivityCompat.requestPermissions(this, permissions.toTypedArray(), requestCode)
    }
    return permissions.isNotEmpty()
}