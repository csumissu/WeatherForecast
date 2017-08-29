package csumissu.weatherforecast.ui.forecasts

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import csumissu.weatherforecast.model.Coordinate
import csumissu.weatherforecast.model.ForecastRepository
import csumissu.weatherforecast.util.ImmediateSchedulerProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

/**
 * @author yxsun
 * @since 29/08/2017
 */
@RunWith(JUnit4::class)
class ForecastsViewModelTest {

    @Rule
    @JvmField
    val mInstantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: ForecastsViewModel
    private lateinit var mRepository: ForecastRepository
    private lateinit var mView: ForecastsContract.ForecastsView
    private lateinit var mLifecycleRegistry: LifecycleRegistry

    @Before
    fun setup() {
        mRepository = mock(ForecastRepository::class.java)
        mView = mock(ForecastsContract.ForecastsView::class.java)
        mLifecycleRegistry = LifecycleRegistry(mView)
        mViewModel = ForecastsViewModel(mRepository, ImmediateSchedulerProvider())

        `when`(mView.lifecycle).thenReturn(mLifecycleRegistry)
        mViewModel.subscribe(mView)
    }

    @Test
    fun testDestroy() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        assertEquals(mLifecycleRegistry.observerCount, 0)
        assertNull(mViewModel.getView())
    }

    @Test
    fun testItemClick() {
        mViewModel.itemClicked(0)
        verify(mView, never()).showDetails(eq(0), anyDouble(), anyDouble())

        val coordinate = Coordinate()
        mViewModel.updateCoordinate(coordinate)

        mViewModel.itemClicked(0)
        verify(mView, never()).showDetails(0, coordinate.latitude, coordinate.longitude)

        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
        mViewModel.itemClicked(0)
        verify(mView).showDetails(0, coordinate.latitude, coordinate.longitude)
    }

}