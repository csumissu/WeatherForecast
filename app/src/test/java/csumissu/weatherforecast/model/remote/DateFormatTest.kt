package csumissu.weatherforecast.model.remote

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.util.*

/**
 * @author yxsun
 * @since 12/06/2017
 */
class DateFormatTest {

    @Test
    fun testLongToString() {
        val dt = 1497229200_000
        val date = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US).format(dt)
        assertEquals(date, "Jun 12, 2017")
    }

}