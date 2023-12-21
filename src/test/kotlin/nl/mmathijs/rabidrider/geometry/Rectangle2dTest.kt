package nl.mmathijs.rabidrider.geometry

import com.acmerobotics.roadrunner.geometry.Vector2d
import kotlin.math.sqrt
import kotlin.test.*

class Rectangle2dTest {

    @Test
    fun containsPointTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))

        assertTrue(rectangle.contains(Vector2d(0.5, 0.5)))
        assertTrue(rectangle.contains(Vector2d(0.0, 0.0)))
        assertTrue(rectangle.contains(Vector2d(1.0, 1.0)))
        assertFalse(rectangle.contains(Vector2d(1.5, 1.5)))
        assertFalse(rectangle.contains(Vector2d(-0.5, -0.5)))
    }

    @Test
    fun containsRectangleTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))

        assertTrue(rectangle.contains(Rectangle2d(Vector2d(0.5, 0.5), Vector2d(0.6, 0.6))))
        assertTrue(rectangle.contains(Rectangle2d(Vector2d(0.0, 0.0), Vector2d(0.6, 0.6))))
        assertTrue(rectangle.contains(Rectangle2d(Vector2d(0.5, 0.5), Vector2d(1.0, 1.0))))
        assertFalse(rectangle.contains(Rectangle2d(Vector2d(0.5, 0.5), Vector2d(1.5, 1.5))))
        assertFalse(rectangle.contains(Rectangle2d(Vector2d(-0.5, -0.5), Vector2d(0.5, 0.5))))
    }

    @Test
    fun lineIntersectsRectangleTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))

        assertTrue(rectangle.intersects(Line2d(Vector2d(-1.0, -1.0), Vector2d(2.0, 2.0))))
        assertTrue(rectangle.intersects(Line2d(Vector2d(0.5, 0.5), Vector2d(1.5, 1.5))))
    }

    @Test
    fun distanceToIntersectionTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))

        assertEquals(
            sqrt(2.0), rectangle.distanceIntersection(
                Line2d(Vector2d(-1.0, -1.0), Vector2d(2.0, 2.0))
            )
        )

        assertEquals(
            sqrt(0.5), rectangle.distanceIntersection(
                Line2d(Vector2d(0.5, 0.5), Vector2d(1.5, 1.5))
            )
        )

        assertEquals(
            Double.POSITIVE_INFINITY, rectangle.distanceIntersection(
                Line2d(Vector2d(0.5, 0.5), Vector2d(0.6, 0.6))
            )
        )

        assertEquals(
            0.5, rectangle.distanceIntersection(
                Line2d(Vector2d(-1.0, 0.5), Vector2d(2.0, 0.5)),
                Vector2d(-0.5, 0.5)
            )
        )

        assertEquals(
            0.5, rectangle.distanceIntersection(
                Line2d(Vector2d(0.5, -1.0), Vector2d(0.5, 2.0)),
                Vector2d(0.5, -0.5)
            )
        )
    }

    @Test
    fun intersectionsTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))

        assertTrue(
            listEqualsWithoutOrder(
                listOf(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0)),
                rectangle.intersections(Line2d(Vector2d(-1.0, -1.0), Vector2d(2.0, 2.0)))
            )
        )

        assertTrue(
            listEqualsWithoutOrder(
                listOf(Vector2d(1.0, 1.0)),
                rectangle.intersections(Line2d(Vector2d(0.5, 0.5), Vector2d(1.5, 1.5)))
            )
        )

        assertEquals(
            listOf<Vector2d>(),
            rectangle.intersections(Line2d(Vector2d(2.0, 2.0), Vector2d(3.0, 3.0)))
        )

        assertEquals(
            listOf<Vector2d>(),
            rectangle.intersections(Line2d(Vector2d(-1.0, -1.0), Vector2d(-2.0, -2.0)))
        )

        assertEquals(
            listOf<Vector2d>(),
            rectangle.intersections(Line2d(Vector2d(0.5, 0.5), Vector2d(0.6, 0.6)))
        )

        assertTrue(
            listEqualsWithoutOrder(
                listOf<Vector2d>(Vector2d(0.0, 0.5), Vector2d(1.0, 0.5)),
                rectangle.intersections(Line2d(Vector2d(-1.0, 0.5), Vector2d(2.0, 0.5)))
            )
        )

        assertTrue(
            listEqualsWithoutOrder(
                listOf<Vector2d>(Vector2d(0.5, 0.0), Vector2d(0.5, 1.0)),
                rectangle.intersections(Line2d(Vector2d(0.5, -1.0), Vector2d(0.5, 2.0)))
            )
        )
    }

    private fun listEqualsWithoutOrder(first: List<Vector2d>, second: List<Vector2d>): Boolean {
        return first.containsAll(second) && second.containsAll(first) && first.size == second.size
    }
}