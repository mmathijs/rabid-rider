package nl.mmathijs.rabidrider.geometry.bezier

import com.acmerobotics.roadrunner.geometry.Vector2d
import kotlin.math.pow

class CubicBezier2d(
    @JvmField val point1: Vector2d,
    @JvmField val point2: Vector2d,
    @JvmField val point3: Vector2d,
    @JvmField val point4: Vector2d
) : BaseBezier2d() {

    fun getPoint1() = point1
    fun getPoint2() = point2
    fun getPoint3() = point3
    fun getPoint4() = point4

    override fun getPoint(t: Double): Vector2d {
        val x = (1 - t).pow(3.0) * point1.x +
                3 * (1 - t).pow(2.0) * t * point2.x + 3 * (1 - t) * t.pow(2.0) * point3.x +
                t.pow(3.0) * point4.x

        val y = (1 - t).pow(3.0) * point1.y +
                3 * (1 - t).pow(2.0) * t * point2.y + 3 * (1 - t) * t.pow(2.0) * point3.y +
                t.pow(3.0) * point4.y

        return Vector2d(x, y)
    }
}