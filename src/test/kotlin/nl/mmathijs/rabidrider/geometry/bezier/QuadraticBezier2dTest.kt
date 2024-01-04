package nl.mmathijs.rabidrider.geometry.bezier

import com.acmerobotics.roadrunner.geometry.Vector2d
import org.junit.jupiter.api.Test

class QuadraticBezier2dTest {

    @Test
    fun testQuadraticBezier2d() {
        val bezier = QuadraticBezier2d(
            Vector2d(0.0, 0.0),
            Vector2d(1.0, 1.0),
            Vector2d(2.0, 0.0)
        )

        val point1 = bezier.getPoint1()
        val point2 = bezier.getPoint2()
        val point3 = bezier.getPoint3()

        assert(point1.x == 0.0)
        assert(point1.y == 0.0)
        assert(point2.x == 1.0)
        assert(point2.y == 1.0)
        assert(point3.x == 2.0)
        assert(point3.y == 0.0)

        val point = bezier.getPoint(0.5)

        assert(point.x == 1.0)
        assert(point.y == 0.5)
    }
}