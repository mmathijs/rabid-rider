package nl.mmathijs.rabidrider.geometry

import com.acmerobotics.roadrunner.geometry.Vector2d
import kotlin.math.sqrt
import kotlin.test.*

class Rectangle2dTest {
    private fun listEqualsWithoutOrder(first: List<Any>, second: List<Any>): Boolean {
        return first.containsAll(second) && second.containsAll(first) && first.size == second.size
    }

    private fun rectangleEquals(rectangle1: Rectangle2d, rectangle2: Rectangle2d): Boolean =
        (rectangle1.point1 == rectangle2.point1 && rectangle1.point2 == rectangle2.point2) ||
                (rectangle1.point1 == rectangle2.point2 && rectangle1.point2 == rectangle2.point1)

    private fun line2dListEqualsWithoutOrder(first: List<Line2d>, second: List<Line2d>): Boolean {
        return first.stream().allMatch { line1 ->
            second.stream().anyMatch { line2 ->
                line1.point1 == line2.point1 && line1.point2 == line2.point2 ||
                        line1.point1 == line2.point2 && line1.point2 == line2.point1
            }
        } && second.stream().allMatch { line1 ->
            first.stream().anyMatch { line2 ->
                line1.point1 == line2.point1 && line1.point2 == line2.point2 ||
                        line1.point1 == line2.point2 && line1.point2 == line2.point1
            }
        } && first.size == second.size
    }

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

    @Test
    fun growTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(1.0, 1.0))

        assert(
            rectangleEquals(
                rectangle.grow(Vector2d(0.5, 0.5)),
                Rectangle2d(Vector2d(-0.5, -0.5), Vector2d(1.5, 1.5))
            )
        )

        assert(
            rectangleEquals(
                rectangle.grow(0.5),
                Rectangle2d(Vector2d(-0.5, -0.5), Vector2d(1.5, 1.5))
            )
        )
    }

    @Test
    fun shrinkTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assert(
            rectangleEquals(
                rectangle.shrink(Vector2d(0.5, 0.5)),
                Rectangle2d(Vector2d(0.5, 0.5), Vector2d(1.5, 1.5))
            )
        )

        assert(
            rectangleEquals(
                rectangle.shrink(0.5),
                Rectangle2d(Vector2d(0.5, 0.5), Vector2d(1.5, 1.5))
            )
        )

        assert(
            rectangleEquals(
                rectangle.shrink(Vector2d(1.0, 1.0)),
                Rectangle2d(Vector2d(1.0, 1.0), Vector2d(1.0, 1.0))
            )
        )
    }

    @Test
    fun scaleFromOriginTest() {
        val rectangle = Rectangle2d(Vector2d(2.0, 2.0), Vector2d(4.0, 4.0))

        assert(
            rectangleEquals(
                rectangle.scaleFromOrigin(Vector2d(2.0, 2.0)),
                Rectangle2d(Vector2d(4.0, 4.0), Vector2d(8.0, 8.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.scaleFromOrigin(2.0),
                Rectangle2d(Vector2d(4.0, 4.0), Vector2d(8.0, 8.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.scaleFromOrigin(Vector2d(0.5, 0.5)),
                Rectangle2d(Vector2d(1.0, 1.0), Vector2d(2.0, 2.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.scaleFromOrigin(0.5),
                Rectangle2d(Vector2d(1.0, 1.0), Vector2d(2.0, 2.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.scaleFromOrigin(Vector2d(0.5, 2.0)),
                Rectangle2d(Vector2d(1.0, 4.0), Vector2d(2.0, 8.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.scaleFromOrigin(Vector2d(2.0, 0.5)),
                Rectangle2d(Vector2d(4.0, 1.0), Vector2d(8.0, 2.0))
            )
        )
    }

    @Test
    fun scaleFromCenterTest() {
        val rectangle = Rectangle2d(Vector2d(2.0, 2.0), Vector2d(4.0, 4.0))

        assert(
            rectangleEquals(
                rectangle.scaleFromCenter(Vector2d(2.0, 2.0)),
                Rectangle2d(Vector2d(1.0, 1.0), Vector2d(5.0, 5.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.scaleFromCenter(0.5),
                Rectangle2d(Vector2d(2.5, 2.5), Vector2d(3.5, 3.5))
            )
        )
    }

    @Test
    fun containsLineTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assertTrue(rectangle.contains(Line2d(Vector2d(0.5, 0.5), Vector2d(1.5, 1.5))))
        assertTrue(rectangle.contains(Line2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))))

        assertFalse(rectangle.contains(Line2d(Vector2d(0.5, 0.5), Vector2d(2.5, 1.5))))
        assertFalse(rectangle.contains(Line2d(Vector2d(-0.5, -0.5), Vector2d(1.5, 1.5))))
    }

    @Test
    fun containsVectorTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assertTrue(rectangle.contains(Vector2d(0.5, 0.5)))
        assertTrue(rectangle.contains(Vector2d(0.0, 0.0)))
        assertTrue(rectangle.contains(Vector2d(2.0, 2.0)))

        assertFalse(rectangle.contains(Vector2d(0.5, 2.5)))
        assertFalse(rectangle.contains(Vector2d(-0.5, -0.5)))

        val rectangle2 = Rectangle2d(Vector2d(2.0, 2.0), Vector2d(0.0, 0.0))

        assertTrue(rectangle2.contains(Vector2d(0.5, 0.5)))
        assertTrue(rectangle2.contains(Vector2d(0.0, 0.0)))
        assertTrue(rectangle2.contains(Vector2d(2.0, 2.0)))

        assertFalse(rectangle2.contains(Vector2d(0.5, 2.5)))
        assertFalse(rectangle2.contains(Vector2d(-0.5, -0.5)))
    }

    @Test
    fun getPointTests() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assertEquals(Vector2d(0.0, 0.0), rectangle.getPoint1())
        assertEquals(Vector2d(2.0, 2.0), rectangle.getPoint2())
    }

    @Test
    fun linesTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assertTrue(
            line2dListEqualsWithoutOrder(
                listOf(
                    Line2d(Vector2d(0.0, 0.0), Vector2d(2.0, 0.0)),
                    Line2d(Vector2d(2.0, 0.0), Vector2d(2.0, 2.0)),
                    Line2d(Vector2d(2.0, 2.0), Vector2d(0.0, 2.0)),
                    Line2d(Vector2d(0.0, 2.0), Vector2d(0.0, 0.0))
                ),
                rectangle.lines()
            )
        )
    }

    @Test
    fun translateTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assert(
            rectangleEquals(
                rectangle.translate(Vector2d(1.0, 1.0)),
                Rectangle2d(Vector2d(1.0, 1.0), Vector2d(3.0, 3.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.translate(1.0, 1.0),
                Rectangle2d(Vector2d(1.0, 1.0), Vector2d(3.0, 3.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.translate(Vector2d(-1.0, -1.0)),
                Rectangle2d(Vector2d(-1.0, -1.0), Vector2d(1.0, 1.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.translate(-1.0, -1.0),
                Rectangle2d(Vector2d(-1.0, -1.0), Vector2d(1.0, 1.0))
            )
        )

        assert(
            rectangleEquals(
                rectangle.translate(Vector2d(1.0, -1.0)),
                Rectangle2d(Vector2d(1.0, -1.0), Vector2d(3.0, 1.0))
            )
        )
    }

    @Test
    fun getCenterTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assertEquals(Vector2d(1.0, 1.0), rectangle.getCenter())

        val rectangle2 = Rectangle2d(Vector2d(2.0, 2.0), Vector2d(0.0, 0.0))

        assertEquals(Vector2d(1.0, 1.0), rectangle2.getCenter())
    }

    @Test
    fun getPerimeterTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assertEquals(8.0, rectangle.getPerimeter())
    }

    @Test
    fun surfaceAreaTest() {
        val rectangle = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))

        assertEquals(4.0, rectangle.getArea())
    }

    @Test
    fun intersectsRectangleTest() {
        val rectangle1 = Rectangle2d(Vector2d(0.0, 0.0), Vector2d(2.0, 2.0))
        val rectangle2 = Rectangle2d(Vector2d(1.0, 1.0), Vector2d(3.0, 3.0))

        assertTrue(rectangle1.intersects(rectangle2))
        assertTrue(rectangle2.intersects(rectangle1))

        val rectangle3 = Rectangle2d(Vector2d(3.0, 3.0), Vector2d(4.0, 4.0))

        assertFalse(rectangle1.intersects(rectangle3))
        assertFalse(rectangle3.intersects(rectangle1))
    }
}