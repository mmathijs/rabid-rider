package nl.mmathijs.rabidrider.geometry.bezier

import com.acmerobotics.roadrunner.geometry.Vector2d
import nl.mmathijs.rabidrider.geometry.Line2d
import nl.mmathijs.rabidrider.geometry.bezier.CubicBezier2d
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CubicBezier2dTest {

    @Test
    fun testCubicBezier2d() {
        val bezier = CubicBezier2d(
            Vector2d(0.0, 0.0),
            Vector2d(1.0, 1.0),
            Vector2d(2.0, 1.0),
            Vector2d(3.0, 0.0)
        )

        val point1 = bezier.getPoint1()
        val point2 = bezier.getPoint2()
        val point3 = bezier.getPoint3()
        val point4 = bezier.getPoint4()

        assert(point1.x == 0.0)
        assert(point1.y == 0.0)
        assert(point2.x == 1.0)
        assert(point2.y == 1.0)
        assert(point3.x == 2.0)
        assert(point3.y == 1.0)
        assert(point4.x == 3.0)
        assert(point4.y == 0.0)

        val point = bezier.getPoint(0.5)

        assert(point.x == 1.5)
        assert(point.y == 0.75)
    }

    @Test
    fun testCubicBezier2d2() {
        val bezier = CubicBezier2d(
            Vector2d(0.0, 0.0),
            Vector2d(0.0, 1.0),
            Vector2d(1.0, 1.0),
            Vector2d(1.0, 0.0)
        )

        val point = bezier.getPoint(0.5)

        assert(point.x == 0.5)
        assert(point.y == 0.75)
    }

    @Test
    fun testCubicBezier2dIntersect() {
        val bezier = CubicBezier2d(
            Vector2d(0.0, 0.0),
            Vector2d(0.0, 1.0),
            Vector2d(1.0, 1.0),
            Vector2d(1.0, 0.0)
        )

        val line = Line2d(Vector2d(0.0, 0.75), Vector2d(1.0, 0.75))

        assert(bezier.intersect(line) == Vector2d(0.5, 0.75))
        assert(bezier.intersect(line, false) == Vector2d(0.5, 0.75))

        val line2 = Line2d(Vector2d(-1.0, 0.0), Vector2d(2.0, 0.0))

        assertNotNull(bezier.intersect(line2))
        assertNull(bezier.intersect(line2, false))
    }
}