package csumissu.weatherforecast.ui.details

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import csumissu.weatherforecast.MainActivity
import csumissu.weatherforecast.R
import csumissu.weatherforecast.common.BaseActivity
import csumissu.weatherforecast.databinding.ActivityDetailsBinding
import csumissu.weatherforecast.di.Injectable
import csumissu.weatherforecast.extensions.DelegatesExt
import csumissu.weatherforecast.extensions.toDateString
import csumissu.weatherforecast.model.ForecastRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.lay_toolbar.view.*
import org.jetbrains.anko.error
import java.text.DateFormat
import javax.inject.Inject

/**
 * @author yxsun
 * @since 16/08/2017
 */
class DetailsActivity : BaseActivity(), Injectable {

    private var mLocalityPref by DelegatesExt.preference<String>(this, MainActivity.PREF_LOCALITY)
    private lateinit var mDataBinding: ActivityDetailsBinding

    @Inject
    lateinit var mForecastRepository: ForecastRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        setSupportActionBar(mDataBinding.content.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = mLocalityPref

        loadForecast(intent.getDoubleExtra(KEY_LATITUDE, 0.0),
                intent.getDoubleExtra(KEY_LONGITUDE, 0.0),
                intent.getIntExtra(KEY_INDEX, -1))
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun loadForecast(latitude: Double, longitude: Double, index: Int) {
        mForecastRepository.loadForecast(latitude, longitude, index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    supportActionBar?.subtitle = it.date.toDateString(DateFormat.FULL)
                    mDataBinding.forecast = it
                }, { throwable ->
                    error("Can't load forecast by $index", throwable)
                    finish()
                })
    }

    companion object {
        val KEY_INDEX = "index"
        val KEY_LATITUDE = "latitude"
        val KEY_LONGITUDE = "longitude"
    }

}