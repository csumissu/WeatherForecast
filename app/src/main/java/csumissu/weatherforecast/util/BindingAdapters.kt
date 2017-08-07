package csumissu.weatherforecast.util

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import csumissu.weatherforecast.R
import csumissu.weatherforecast.extensions.color
import csumissu.weatherforecast.extensions.textColor

/**
 * @author yxsun
 * @since 06/08/2017
 */
object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        ImageUtils.loadImage(view, url, R.drawable.ic_photo_placeholder, 0)
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("temperature")
    fun bindTextColor(view: TextView, temperature: Float) {
        view.text = "$temperature°"
        view.textColor = view.context.color(when (temperature) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }

}