package csumissu.weatherforecast.ui

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import csumissu.weatherforecast.MainActivity
import csumissu.weatherforecast.R
import csumissu.weatherforecast.adapter.ForecastsAdapter
import csumissu.weatherforecast.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_forecasts.*

/**
 * @author yxsun
 * @since 01/06/2017
 */
class ForecastsFragment : BaseFragment() {

    private lateinit var mAdapter: ForecastsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecasts, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mForecastsView.layoutManager = LinearLayoutManager(context)
        mAdapter = ForecastsAdapter { it ->
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).showDetails(it)
            }
        }
        mForecastsView.adapter = mAdapter
    }

}