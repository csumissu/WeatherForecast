package csumissu.weatherforecast.util

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


/**
 * @author yxsun
 * @since 07/06/2017
 */
object ActivityUtils {

    fun showFragment(fragmentManager: FragmentManager, fragment: Fragment,
                     @IdRes frameId: Int, tag: String? = null) {
        val transaction = fragmentManager.beginTransaction()
        if (!fragment.isAdded) {
            transaction.add(frameId, fragment, tag)
        }
        transaction.show(fragment).commit()
    }

    fun hideFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        fragmentManager.beginTransaction().hide(fragment).commit()
    }

    fun showFragmentInTx(fragmentManager: FragmentManager, fragment: Fragment,
                         @IdRes frameId: Int, tag: String? = null) {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(frameId, fragment, tag)
                .commit()
    }

    inline fun <reified T> findFragmentByTag(fragmentManager: FragmentManager, tag: String): T? {
        return fragmentManager.findFragmentByTag(tag) as T?
    }

}