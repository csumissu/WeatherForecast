package csumissu.weatherforecast.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author yxsun
 * @since 15/08/2017
 */
interface BaseSchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
}

class ImmediateSchedulerProvider : BaseSchedulerProvider {
    override fun computation() = Schedulers.single()
    override fun io() = Schedulers.single()
    override fun ui() = Schedulers.single()
}

class SchedulerProvider : BaseSchedulerProvider {
    override fun computation() = Schedulers.computation()
    override fun io() = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}