package csumissu.weatherforecast.model.remote

import csumissu.weatherforecast.data.Coordinate
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author yxsun
 * @since 13/06/2017
 */
class DefaultValueTest {

    @Test
    fun testCoordinate() {
        val coordinate = Coordinate()
        assertEquals(coordinate.latitude, 34.275, 0.0)
    }

    @Test
    fun testCoordinate2() {
        val coordinate = Coordinate(1.0)
        assertEquals(coordinate.latitude, 1.0, 0.0)
    }

    @Test
    fun testCoordinate3() {
        val coordinate = Coordinate(1.0, 2.0)
        assertEquals(coordinate.latitude, 1.0, 0.0)
        assertEquals(coordinate.longitude, 2.0, 0.0)
    }

    @Test
    fun testCoordinate4() {
        val coordinate = Coordinate(_longitude = 2.0)
        assertEquals(coordinate.latitude, 34.275, 0.0)
        assertEquals(coordinate.longitude, 2.0, 0.0)
    }

}