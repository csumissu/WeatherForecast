package csumissu.weatherforecast.extensions

import android.content.Context
import android.view.View
import android.widget.TextView

/**
 * @author yxsun
 * @since 05/06/2017
 */
val View.ctx: Context
    get() = context

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)