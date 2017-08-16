package csumissu.weatherforecast.util

import android.content.Context
import android.util.TypedValue
import csumissu.weatherforecast.App
import csumissu.weatherforecast.extensions.connectivityManager
import org.jetbrains.anko.displayMetrics

/**
 * @author yxsun
 * @since 15/08/2017
 */
object Utils {

    fun isNetworkAvailable(context: Context): Boolean {
        val info = context.connectivityManager.activeNetworkInfo
        if (info != null) {
            return info.isAvailable && info.isConnected
        }
        return false
    }

    fun sp2px(sp: Number): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(),
                App.INSTANCE.displayMetrics)
    }

    fun dp2px(dp: Number): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
                App.INSTANCE.displayMetrics)
    }

}