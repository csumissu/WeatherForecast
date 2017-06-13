package csumissu.weatherforecast.ui

import android.os.Bundle
import csumissu.weatherforecast.common.BaseFragment
import csumissu.weatherforecast.model.Forecast

/**
 * @author yxsun
 * @since 07/06/2017
 */
class DetailsFragment : BaseFragment() {


    companion object {
        val KEY_FORECAST = "key_forecast"

        fun forForecast(forecast: Forecast): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val args = Bundle()
            detailsFragment.arguments = args
            return detailsFragment
        }
    }

}