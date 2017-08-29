package csumissu.weatherforecast.ui.forecasts

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import csumissu.weatherforecast.common.runSafely
import csumissu.weatherforecast.exception.NetResponseFunc
import csumissu.weatherforecast.exception.NetSubscriber
import csumissu.weatherforecast.exception.ResponseException
import csumissu.weatherforecast.model.Coordinate
import csumissu.weatherforecast.model.ForecastList
import csumissu.weatherforecast.model.ForecastRepository
import csumissu.weatherforecast.util.BaseSchedulerProvider
import javax.inject.Inject


/**
 * @author yxsun
 * @since 11/06/2017
 */
class ForecastsViewModel
@Inject constructor(private val mForecastRepository: ForecastRepository,
                    private val mSchedulerProvider: BaseSchedulerProvider)
    : ViewModel(), ForecastsContract.ForecastsPresenter {

    private var mView: ForecastsContract.ForecastsView? = null
    private val mLocation = MutableLiveData<Coordinate>()
    private val mForecasts = Transformations.switchMap(mLocation) {
        val data = MutableLiveData<ForecastList>()
        loadForecasts(it, data)
        data
    }

    override fun subscribe(view: ForecastsContract.ForecastsView) {
        super.subscribe(view)
        mView = view
        mForecasts.observe(mView, Observer {
            mView?.showForecasts(it)
        })
    }

    override fun unsubscribe() {
        mView = null
    }

    @VisibleForTesting
    fun getView() = mView

    override fun updateCoordinate(coordinate: Coordinate) {
        mLocation.value = coordinate
    }

    override fun itemClicked(position: Int) {
        mLocation.value?.let {
            mView.runSafely { showDetails(position, it.latitude, it.longitude) }
        }
    }

    private fun loadForecasts(coordinate: Coordinate, data: MutableLiveData<ForecastList>) {
        mForecastRepository
                .loadDailyForecasts(coordinate.latitude, coordinate.longitude)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .onErrorResumeNext(NetResponseFunc<ForecastList>())
                .subscribe(object : NetSubscriber<ForecastList>(mView) {
                    override fun onFailed(error: ResponseException) {
                        mView.runSafely {
                            if (error.displayMessage > 0) {
                                showError(error.displayMessage)
                            } else {
                                showError(error.message)
                            }
                        }
                    }

                    override fun onNext(t: ForecastList?) {
                        data.value = t
                    }
                })
    }

}