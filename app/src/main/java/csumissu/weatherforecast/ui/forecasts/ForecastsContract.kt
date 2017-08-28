package csumissu.weatherforecast.ui.forecasts

import android.support.annotation.StringRes
import csumissu.weatherforecast.common.BaseContract
import csumissu.weatherforecast.model.Coordinate
import csumissu.weatherforecast.model.ForecastList


/**
 * @author yxsun
 * @since 27/08/2017
 */
interface ForecastsContract {

    interface ForecastsView : BaseContract.IView {

        fun showForecasts(forecastList: ForecastList?)

        fun showDetails(position: Int, latitude: Double, longitude: Double)

        fun showError(message: String?)

        fun showError(@StringRes displayMsgId: Int)

    }

    interface ForecastsPresenter : BaseContract.IPresenter<ForecastsView> {

        fun updateCoordinate(coordinate: Coordinate)

        fun itemClicked(position: Int)

    }

}