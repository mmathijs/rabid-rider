package nl.mmathijs.rabidrider.geometry

import com.acmerobotics.roadrunner.geometry.Vector2d
import nl.mmathijs.rabidrider.geometry.Line2d
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class Line2dTest {

    @Test
    fun testLine2d() {
        val line = Line2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))

        assertEquals(sqrt(2.0), line.getLength())
        assertEquals(Vector2d(0.5, 0.5), line.getMidpoint())
        assertEquals(PI / 4, line.getAngle())

        assertEquals(Vector2d(0.5, 0.5), line.getPoint(0.5))
        assertEquals(Vector2d(0.0, 0.0), line.getPoint1())
        assertEquals(Vector2d(1.0, 1.0), line.getPoint2())
    }

    @Test
    fun lineIntersectTest() {
        val line1 = Line2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))
        val line2 = Line2d(Vector2d(0.0, 1.0), Vector2d(1.0, 0.0))

        assertTrue(line1.intersects(line2))
        assertTrue(line1.intersects(line2, false))

        val line3 = Line2d(Vector2d(0.0, 0.0), Vector2d(1.0, 0.0))

        assertTrue(line1.intersects(line3, true))
        assertFalse(line1.intersects(line3, false))

        val line4 = Line2d(Vector2d(0.0, 1.0), Vector2d(1.0, 2.0))

        assertFalse(line1.intersects(line4))
        assertFalse(line1.intersects(line4, false))
    }

    @Test
    fun lineIntersectionsTest() {
        val line1 = Line2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))
        val line2 = Line2d(Vector2d(0.0, 1.0), Vector2d(1.0, 0.0))

        assertEquals(Vector2d(0.5, 0.5), line1.intersection(line2))

        val line3 = Line2d(Vector2d(0.0, 0.0), Vector2d(1.0, 0.0))

        assertEquals(Vector2d(0.0, 0.0), line1.intersection(line3))
        assertNull(line1.intersection(line3, false))

        val line4 = Line2d(Vector2d(0.0, 1.0), Vector2d(1.0, 2.0))

        assertNull(line1.intersection(line4))
    }
}