package csumissu.weatherforecast.ui.forecasts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import csumissu.weatherforecast.R
import csumissu.weatherforecast.extensions.ctx
import csumissu.weatherforecast.extensions.inflate
import csumissu.weatherforecast.extensions.toDateString
import csumissu.weatherforecast.model.Forecast
import csumissu.weatherforecast.model.ForecastList
import csumissu.weatherforecast.util.ImageUtils
import kotlinx.android.synthetic.main.item_forecast.view.*

/**
 * @author yxsun
 * @since 07/06/2017
 */
class ForecastsAdapter(private var mForecastList: ForecastList? = null,
                       private val mItemClick: ((Int) -> Unit)?)
    : RecyclerView.Adapter<ForecastsAdapter.ViewHolder>() {

    fun setData(forecastList: ForecastList?) {
        mForecastList = forecastList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.ctx.inflate(R.layout.item_forecast, parent))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mForecastList?.get(position)?.let {
            holder.bindForecast(it)
            holder.itemView.setOnClickListener { mItemClick?.invoke(position) }
        }
    }

    override fun getItemCount() = mForecastList?.size ?: 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: Forecast) = with(forecast) {
            val weather = weathers[0]

            ImageUtils.loadImage(itemView.icon, weather.iconUrl)
            itemView.date.text = date.toDateString()
            itemView.description.text = weather.description
            itemView.maxTemperature.text = "${temperature.max}°"
            itemView.minTemperature.text = "${temperature.min}°"
        }
    }
}