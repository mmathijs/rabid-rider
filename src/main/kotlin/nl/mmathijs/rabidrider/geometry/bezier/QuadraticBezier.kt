package nl.mmathijs.rabidrider.geometry.bezier

import com.acmerobotics.roadrunner.geometry.Vector2d
import kotlin.math.pow

class QuadraticBezier(
    @JvmField val point1: Vector2d,
    @JvmField val point2: Vector2d,
    @JvmField val point3: Vector2d) : BaseBezier2d() {

    fun getPoint1() = point1
    fun getPoint2() = point2
    fun getPoint3() = point3

    override fun getPoint(t: Double): Vector2d {
        val x = (1 - t).pow(2.0) * point1.x +
                2 * (1 - t) * t * point2.x +
                t.pow(2.0) * point3.x

        val y = (1 - t).pow(2.0) * point1.y +
                2 * (1 - t) * t * point2.y +
                t.pow(2.0) * point3.y

        return Vector2d(x, y)
    }
}