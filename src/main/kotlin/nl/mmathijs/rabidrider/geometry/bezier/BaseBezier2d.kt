package nl.mmathijs.rabidrider.geometry.bezier

import com.acmerobotics.roadrunner.geometry.Vector2d
import nl.mmathijs.rabidrider.geometry.Line2d

abstract class BaseBezier2d {

    /**
     * Gets the point on the bezier at a certain t value.
     * @param t The t value to get the point at.
     *
     * @return The point on the bezier at the given t value.
     */
    abstract fun getPoint(t: Double): Vector2d

    /**
     * Approximates the length of the bezier.
     * @param segmentCount The amount of segments to use for the approximation.
     *
     * @return The approximate length of the bezier.
     */
    fun getLength(segmentCount: Int = 100): Double {
        var length = 0.0
        var lastPoint = getPoint(0.0)
        for (i in 1..segmentCount) {
            val point = getPoint(i / segmentCount.toDouble())
            length += point.distTo(lastPoint)
            lastPoint = point
        }
        return length
    }

    /**
     * Intersects this bezier with a line.
     * @param other The line to intersect with.
     * @param includeEnd Whether or not to include the end points of the segments in the intersection check.
     * @param segmentCount The amount of segments to use for the approximation.
     *
     * This function is NOT recommended for use in a real-time environment. It is can get very slow for complex beziers or a high segment count.
     *
     * @return The FIRST intersection point or null if there is no intersection.
     */
    fun intersect(other: Line2d, includeEnd: Boolean = true, segmentCount: Int = 100): Vector2d? {
        var lastPoint = getPoint(0.0)

        for (i in 1..segmentCount) {
            val point = getPoint(i / segmentCount.toDouble())
            val line = Line2d(lastPoint, point)
            val intersection = line.intersection(other, includeEnd)
            if (intersection != null) {
                return intersection
            }
            lastPoint = point
        }
        return null
    }

    /**
     * Intersects this bezier with another bezier.
     * @param other The other bezier to intersect with.
     * @param includeEnd Whether or not to include the end points of the segments in the intersection check.
     * @param segmentCount The amount of segments to use for the approximation.
     *
     * This function is NOT recommended for use in a real-time environment. It is can get very slow for complex beziers or a high segment count.
     *
     * @return The FIRST intersection point or null if there is no intersection.
     */
    fun intersect(other: BaseBezier2d, includeEnd: Boolean = true, segmentCount: Int = 100): Vector2d? {
        var lastPoint = getPoint(0.0)

        for (i in 1..segmentCount) {
            val point = getPoint(i / segmentCount.toDouble())
            val line = Line2d(lastPoint, point)
            val intersection = other.intersect(line, includeEnd, segmentCount)
            if (intersection != null) {
                return intersection
            }
            lastPoint = point
        }
        return null
    }
}