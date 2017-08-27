package csumissu.weatherforecast.common

/**
 * @author yxsun
 * @since 27/08/2017
 */
interface BaseContract {

    interface IView {

        fun showProgress()

        fun hideProgress()

    }

    interface IPresenter {

        fun subscribe()

        fun unsubscribe()

    }

}