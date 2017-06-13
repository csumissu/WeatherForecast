package csumissu.weatherforecast.model.remote

import csumissu.weatherforecast.model.Coordinate
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * @author yxsun
 * @since 07/06/2017
 */
class ForecastRemoteProviderTest {

    private lateinit var mRemoteProvider: ForecastRemoteProvider

    @Before
    fun setup() {
        mRemoteProvider = ForecastRemoteProvider(ForecastApi.getApiService())
    }

    @Test
    fun testLoadDailyForecasts() {
        val coordinate = Coordinate()
        mRemoteProvider.loadDailyForecasts(coordinate.latitude, coordinate.longitude)
                .subscribe {
                    println(it)
                    assertNotNull(it)
                }

    }

}
