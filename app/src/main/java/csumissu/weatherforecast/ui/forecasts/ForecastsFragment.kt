package csumissu.weatherforecast.ui.forecasts

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import csumissu.weatherforecast.R
import csumissu.weatherforecast.common.BaseFragment
import csumissu.weatherforecast.di.Injectable
import csumissu.weatherforecast.model.Coordinate
import csumissu.weatherforecast.model.ForecastList
import csumissu.weatherforecast.ui.details.DetailsActivity
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity
import javax.inject.Inject

/**
 * @author yxsun
 * @since 01/06/2017
 */
class ForecastsFragment : BaseFragment(), ForecastsContract.ForecastsView, Injectable {

    private val mForecastsView by lazy { find<RecyclerView>(R.id.recycler_view) }
    private val mProgressBar by lazy { find<ContentLoadingProgressBar>(R.id.progress_bar) }

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mAdapter: ForecastsAdapter
    private lateinit var mViewModel: ForecastsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_forecasts, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mForecastsView.layoutManager = LinearLayoutManager(context)
        mAdapter = ForecastsAdapter { mViewModel.itemClicked(it) }
        mForecastsView.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ForecastsViewModel::class.java)
        mViewModel.subscribe(this)
    }

    fun updateCoordinate(coordinate: Coordinate) {
        mViewModel.updateCoordinate(coordinate)
    }

    override fun showProgress() {
        mProgressBar.show()
    }

    override fun hideProgress() {
        mProgressBar.hide()
    }

    override fun showForecasts(forecastList: ForecastList?) {
        mAdapter.setData(forecastList)
    }

    override fun showDetails(position: Int, latitude: Double, longitude: Double) {
        startActivity<DetailsActivity>(
                DetailsActivity.KEY_INDEX to position,
                DetailsActivity.KEY_LATITUDE to latitude,
                DetailsActivity.KEY_LONGITUDE to longitude)
    }

    override fun showError(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun showError(displayMsgId: Int) {
        Toast.makeText(context, displayMsgId, Toast.LENGTH_LONG).show()
    }

}