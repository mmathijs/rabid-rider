package nl.mmathijs.rabidrider.geometry

import com.acmerobotics.roadrunner.geometry.Vector2d


class Rectangle2d(@JvmField val point1: Vector2d, @JvmField val point2: Vector2d) {

    fun getPoint1() = point1
    fun getPoint2() = point2

    fun getCenter() = point1.plus(point2).div(2.0)

    fun getArea() = (point2.x - point1.x) * (point2.y - point1.y)
    fun getPerimeter() = 2 * (point2.x - point1.x) + 2 * (point2.y - point1.y)

    fun translate(vector: Vector2d) = Rectangle2d(point1 + vector, point2 + vector)
    fun translate(x: Double, y: Double) = translate(Vector2d(x, y))

    fun scaleFromOrigin(scale: Double) = Rectangle2d(point1 * scale, point2 * scale)
    fun scaleFromOrigin(scale: Vector2d) =
        Rectangle2d(Vector2d(point1.x * scale.x, point1.y * scale.y), Vector2d(point2.x * scale.x, point2.y * scale.y))

    fun scaleFromCenter(scale: Double) =
        Rectangle2d(getCenter() - (getCenter() - point1) * scale, getCenter() + (point2 - getCenter()) * scale)

    fun scaleFromCenter(scale: Vector2d) =
        Rectangle2d(
            Vector2d(
                getCenter().x - (getCenter().x - point1.x) * scale.x,
                getCenter().y - (getCenter().y - point1.y) * scale.y
            ),
            Vector2d(
                getCenter().x + (point2.x - getCenter().x) * scale.x,
                getCenter().y + (point2.y - getCenter().y) * scale.y
            )
        )


    fun grow(amount: Vector2d): Rectangle2d {
        val amountPoint1X = amount.x * (if (point1.x < point2.x) -1 else 1)
        val amountPoint1Y = amount.y * (if (point1.y < point2.y) -1 else 1)
        val amountPoint2X = amount.x * (if (point1.x > point2.x) -1 else 1)
        val amountPoint2Y = amount.y * (if (point1.y > point2.y) -1 else 1)

        return Rectangle2d(
            Vector2d(point1.x + amountPoint1X, point1.y + amountPoint1Y),
            Vector2d(point2.x + amountPoint2X, point2.y + amountPoint2Y)
        )
    }

    fun grow(amount: Double) = grow(Vector2d(amount, amount))

    fun shrink(amount: Vector2d) = grow(amount.times(-1.0))
    fun shrink(amount: Double) = grow(amount * -1.0)


    fun getLines() = listOf(
        Line2d(point1, Vector2d(point2.x, point1.y)),
        Line2d(Vector2d(point2.x, point1.y), point2),
        Line2d(point2, Vector2d(point1.x, point2.y)),
        Line2d(Vector2d(point1.x, point2.y), point1)
    )

    fun intersects(other: Rectangle2d): Boolean {
        return getLines().any { line -> other.getLines().any { line.intersects(it) } }
    }

    fun intersects(line: Line2d): Boolean {
        return getLines().any { it.intersects(line) }
    }

    fun contains(point: Vector2d): Boolean {
        return point.x in point1.x..point2.x && point.y in point1.y..point2.y
                || point.x in point2.x..point1.x && point.y in point2.y..point1.y
    }

    fun contains(rectangle: Rectangle2d): Boolean {
        return contains(rectangle.point1) && contains(rectangle.point2)
    }

    fun contains(line: Line2d): Boolean {
        return contains(line.point1) && contains(line.point2)
    }

    fun intersections(line: Line2d): List<Vector2d> {
        // It is possible that a line intersects multiple lines, at the intersection of 2 lines, so we use distinct to remove duplicates
        return getLines().mapNotNull { it.getIntersection(line) }.distinct()
    }

    fun distanceIntersection(line: Line2d, point: Vector2d? = null): Double {
        return intersections(line).minOfOrNull {
            it.distTo(point ?: line.point1)
        } ?: Double.POSITIVE_INFINITY
    }
}

