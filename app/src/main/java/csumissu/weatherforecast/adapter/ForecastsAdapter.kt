package csumissu.weatherforecast.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import csumissu.weatherforecast.R
import csumissu.weatherforecast.data.Forecast
import csumissu.weatherforecast.data.ForecastList
import csumissu.weatherforecast.extensions.ctx
import csumissu.weatherforecast.util.ImageUtils
import kotlinx.android.synthetic.main.item_forecast.view.*

/**
 * @author yxsun
 * @since 07/06/2017
 */
class ForecastsAdapter(var forecastList: ForecastList? = null, val itemClick: (Forecast) -> Unit) :
        RecyclerView.Adapter<ForecastsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecastList!![position]
        holder.bindForecast(forecast)
        holder.itemView.setOnClickListener { itemClick(forecast) }
    }

    override fun getItemCount(): Int {
        return forecastList?.size ?: 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: Forecast) = with(forecast) {
            val weather = forecast.weathers[0]

            ImageUtils.loadImage(itemView.icon, weather.iconUrl)
            itemView.date.text = date.toString()
            itemView.description.text = weather.description
            itemView.maxTemperature.text = "${temperature.max}°"
            itemView.minTemperature.text = "${temperature.min}°"
        }
    }
}