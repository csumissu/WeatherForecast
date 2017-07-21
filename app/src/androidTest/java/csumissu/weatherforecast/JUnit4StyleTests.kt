package csumissu.weatherforecast

import android.arch.lifecycle.ViewModelProviders
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.v4.app.Fragment
import csumissu.weatherforecast.adapter.ForecastsAdapter
import csumissu.weatherforecast.extensions.findFragmentByTag
import csumissu.weatherforecast.ui.ForecastsFragment
import csumissu.weatherforecast.viewmodel.ForecastListViewModel
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy
import tools.fastlane.screengrab.locale.LocaleTestRule

/**
 * @author yxsun
 * @since 19/06/2017
 */
@RunWith(JUnit4::class)
class JUnit4StyleTests {
    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java)
    val mIdlingRes = SimpleIdlingResource()

    @Before
    fun beforeEach() {
        Espresso.registerIdlingResources(mIdlingRes)
        mIdlingRes.isIdleNow = false
        val viewModel = getForecastListViewModel()
        viewModel.getDailyForecasts().observeForever {
            if (it != null) {
                mIdlingRes.isIdleNow = true
            }
        }
    }

    @Test
    fun testTakeScreenshot() {
        onView(withId(R.id.mForecastsView)).check(matches(isDisplayed()))
        Screengrab.screenshot("forecasts")
        onView(withId(R.id.mForecastsView)).perform(
                RecyclerViewActions.actionOnItemAtPosition<ForecastsAdapter.ViewHolder>(1, click()))
        Screengrab.screenshot("details")
    }

    private fun getForecastListViewModel(): ForecastListViewModel {
        val activity = mActivityRule.activity
        val forecastsFragment = activity.findFragmentByTag<Fragment>(ForecastsFragment.TAG_NAME)
        return ViewModelProviders.of(forecastsFragment!!).get(ForecastListViewModel::class.java)
    }

    companion object {
        @ClassRule @JvmField val localTestRule = LocaleTestRule()

        @BeforeClass @JvmStatic fun beforeAll() {
            Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
        }
    }

}
