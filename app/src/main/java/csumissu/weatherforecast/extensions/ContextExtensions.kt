package csumissu.weatherforecast.extensions

import android.content.Context
import android.support.v4.content.ContextCompat

/**
 * @author yxsun
 * @since 05/06/2017
 */
fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)