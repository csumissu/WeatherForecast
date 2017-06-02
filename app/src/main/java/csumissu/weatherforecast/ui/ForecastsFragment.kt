package csumissu.weatherforecast.ui

import android.arch.lifecycle.LifecycleFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import csumissu.weatherforecast.R

/**
 * @author yxsun
 * @since 01/06/2017
 */
class ForecastsFragment : LifecycleFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val detailView = inflater!!.inflate(R.layout.fragment_detail, container!!, false)
        return detailView
    }
}