package csumissu.weatherforecast.util

import android.databinding.BindingAdapter
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.squareup.picasso.Picasso
import csumissu.weatherforecast.R
import csumissu.weatherforecast.extensions.ctx

/**
 * @author yxsun
 * @since 07/06/2017
 */
object ImageUtils {

    @JvmStatic
    @BindingAdapter("android:src")
    fun loadImage(view: ImageView, url: String) {
        loadImage(view, url, R.drawable.ic_photo_placeholder, 0)
    }

    fun loadImage(view: ImageView, url: String?,
                  @DrawableRes placeHolder: Int = R.drawable.ic_photo_placeholder,
                  @DrawableRes errorHolder: Int = 0) {
        val builder = Picasso.with(view.ctx).load(url).placeholder(placeHolder)
        if (errorHolder > 0) {
            builder.error(errorHolder)
        }
        builder.into(view)
    }

}