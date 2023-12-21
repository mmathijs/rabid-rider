package nl.mmathijs.rabidrider.geometry

import com.acmerobotics.roadrunner.geometry.Vector2d
import kotlin.math.atan2


class Line2d(@JvmField val point1: Vector2d, @JvmField val point2: Vector2d) {
    fun getLength() = point1.distTo(point2)
    fun getMidpoint() = point1.plus(point2).div(2.0)
    fun getAngle() = atan2(point2.y - point1.y, point2.x - point1.x)

    fun getPoint(t: Double) = point1 + (point2 - point1) * t
    fun getPoint1() = point1
    fun getPoint2() = point2


    fun intersects(other: Line2d, includeEnd: Boolean = true): Boolean {
        return intersection(other, includeEnd) != null
    }

    fun intersection(other: Line2d, includeEnd: Boolean = true): Vector2d? {
        val denominator =
            (other.point2.y - other.point1.y) * (point2.x - point1.x) - (other.point2.x - other.point1.x) * (point2.y - point1.y)
        val numerator1 =
            (other.point2.x - other.point1.x) * (point1.y - other.point1.y) - (other.point2.y - other.point1.y) * (point1.x - other.point1.x)
        val numerator2 =
            (point2.x - point1.x) * (point1.y - other.point1.y) - (point2.y - point1.y) * (point1.x - other.point1.x)

        if (denominator == 0.0) {
            return if (numerator1 == 0.0 && numerator2 == 0.0) {
                point1
            } else {
                null
            }
        }

        val r = numerator1 / denominator
        val s = numerator2 / denominator

        return if (includeEnd) {
            if (r in 0.0..1.0 && s in 0.0..1.0) {
                point1 + (point2 - point1) * r
            } else {
                null
            }
        } else {
            if (r > 0.0 && r < 1.0 && s > 0.0 && s < 1.0) {
                point1 + (point2 - point1) * r
            } else {
                null
            }
        }
    }
}