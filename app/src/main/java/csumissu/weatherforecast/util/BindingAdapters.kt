package csumissu.weatherforecast.util

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import csumissu.weatherforecast.R

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

}