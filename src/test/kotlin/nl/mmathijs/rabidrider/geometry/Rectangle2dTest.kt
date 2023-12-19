package nl.mmathijs.rabidrider.geometry

import com.acmerobotics.roadrunner.geometry.Vector2d
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
}