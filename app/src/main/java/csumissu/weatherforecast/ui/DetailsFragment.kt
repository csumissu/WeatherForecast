package csumissu.weatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import csumissu.weatherforecast.R
import csumissu.weatherforecast.common.BaseFragment
import csumissu.weatherforecast.model.Forecast

/**
 * @author yxsun
 * @since 07/06/2017
 */
class DetailsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

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