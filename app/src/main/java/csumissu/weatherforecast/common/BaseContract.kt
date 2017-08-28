package csumissu.weatherforecast.common

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.OnLifecycleEvent

/**
 * @author yxsun
 * @since 27/08/2017
 */
interface BaseContract {

    interface IView : LifecycleRegistryOwner {

        fun showProgress()

        fun hideProgress()

    }

    interface IPresenter<in T : IView> {

        fun subscribe(view: T) {
            view.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestory() {
                    view.lifecycle.removeObserver(this)
                    unsubscribe()
                }
            })
        }

        /**
         * Should NOT be used outside Presenter.
         * First, assign view to null if view is member field
         * Second, close any resources belong to that view
         */
        fun unsubscribe()

    }

}

inline fun <T : BaseContract.IView> T?.runSafely(body: T.() -> Unit) {
    this?.let {
        val safe = lifecycle?.currentState?.isAtLeast(Lifecycle.State.STARTED)
        if (safe != null && safe) {
            body()
        }
    }
}