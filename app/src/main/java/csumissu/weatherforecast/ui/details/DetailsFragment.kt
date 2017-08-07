package csumissu.weatherforecast.ui.details

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import csumissu.weatherforecast.R
import csumissu.weatherforecast.common.BaseFragment
import csumissu.weatherforecast.databinding.FragmentDetailsBinding
import csumissu.weatherforecast.model.Forecast

/**
 * @author yxsun
 * @since 07/06/2017
 */
class DetailsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.forecast = arguments.getSerializable(KEY_FORECAST) as Forecast
    }

    companion object {
        val KEY_FORECAST = "key_forecast"

        fun forForecast(forecast: Forecast): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val args = Bundle()
            args.putSerializable(KEY_FORECAST, forecast)
            detailsFragment.arguments = args
            return detailsFragment
        }

    }

}