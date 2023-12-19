package nl.mmathijs.rabidrider

import com.acmerobotics.roadrunner.geometry.Vector2d
import nl.mmathijs.rabidrider.geometry.Line2d
import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.sqrt
import kotlin.test.assertEquals

class GeometryTest {

    @Test
    fun testLine2d() {
        val line = Line2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))

        assertEquals(sqrt(2.0), line.getLength())
        assertEquals(Vector2d(0.5, 0.5), line.getMidpoint())
        assertEquals(PI / 4, line.getAngle())
    }
}