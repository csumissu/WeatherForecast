package csumissu.weatherforecast.common

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import csumissu.weatherforecast.extensions.ctx

/**
 * @author yxsun
 * @since 05/06/2017
 */
interface ToolbarManager {

    val mToolbar: Toolbar

    var toolbarTitle: String
        get() = mToolbar.title.toString()
        set(value) {
            mToolbar.title = value
        }

    fun enableHomeAsUp(up: () -> Unit) {
        mToolbar.navigationIcon = createUpDrawable()
        mToolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = DrawerArrowDrawable(mToolbar.ctx).apply { progress = 1f }
}