package csumissu.weatherforecast.ui.forecasts

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import csumissu.weatherforecast.MainActivity
import csumissu.weatherforecast.R
import csumissu.weatherforecast.common.BaseFragment
import csumissu.weatherforecast.di.Injectable
import csumissu.weatherforecast.model.Coordinate
import csumissu.weatherforecast.model.ForecastList
import csumissu.weatherforecast.viewmodel.ForecastsViewModel
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

/**
 * @author yxsun
 * @since 01/06/2017
 */
class ForecastsFragment : BaseFragment(), Injectable {

    private val mForecastsView by lazy { find<RecyclerView>(R.id.recycler_view) }

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mAdapter: ForecastsAdapter
    private lateinit var mViewModel: ForecastsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecasts, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mForecastsView.layoutManager = LinearLayoutManager(context)
        mAdapter = ForecastsAdapter {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).showDetails(it)
            }
        }
        mForecastsView.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ForecastsViewModel::class.java)
        mViewModel.getDailyForecasts().observe(this, Observer<ForecastList> {
            mAdapter.setData(it)
        })
    }

    fun updateCoordinate(coordinate: Coordinate) {
        mViewModel.setCoordinate(coordinate)
    }

}