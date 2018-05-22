@file:Suppress("unused")

package org.bh.tools.ui.generic.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.math.geometry.*
import org.bh.tools.ui.generic.geometry.FractionOval.Companion.controlPointDistanceForCircularCurveBetween
import kotlin.math.*

/**
 * An oval
 *
 * @author Kyli Rouge
 * @since 2017-01-27 027.
 */
interface Oval<NumberType : Number> : BezierPathConvertible {
    /**
     * The rectangle that surrounds this oval
     */
    val boundingRect: ComputableRect<NumberType, ComputablePoint<NumberType>, ComputableSize<NumberType>>
}


/**
 * An implementation of [Oval] that uses [Fraction]s.
 */
public class FractionOval(
        /**
         * The rectangle that surrounds this oval
         */
        override val boundingRect: FractionRect
) : Oval<Fraction> {

    companion object {
        /**
         * The distance from its anchor that a control point must be in order to generate a circular curve. Note that a
         * true circle is impossible with Bézier paths, but this gives an approximation that never wavers more than
         * 0.019608% away from a true circle.
         */
        val controlPointPercentDistanceForCircularCurve: Fraction = (1.0 / 3.0) * (sqrt(7.0) - 1.0) // twice as precise as: (4.0/3.0)*(sqrt(2.0) - 1.0)


        /**
         * The distance that `a` should be from `b` in order to approximate a circular curve, assuming they're on the
         * same axis. This uses [controlPointPercentDistanceForCircularCurve].
         *
         * @param a A component of the first point
         * @param a The same component of the second point
         *
         * @return The same component of the new control point
         */
        fun controlPointDistanceForCircularCurveBetween(a: Fraction, b: Fraction): Fraction {
            return a + ((b - a) * controlPointPercentDistanceForCircularCurve)
        }


        /**
         * Finds the rectangle that comprises the boundaries around a circle of the given radius whose center is at the given point
         *
         * @param radius The radius of the inscribed circle
         * @param center The point at the center of the inscribed circle
         *
         * @return A square which fits perfectly around a circle of the given radius at the given center point
         */
        fun boundingRectAroundCircle(radius: Fraction, center: Point<Fraction>): FractionRect
                = FractionRect(x = center.x - radius, y = center.y - radius, width = radius * 2, height = radius * 2)
    }


    /**
     * Creates a circle of the given radius, centered about the given point
     */
    constructor(circularRadius: Fraction, center: Point<Fraction>): this(boundingRectAroundCircle(radius = circularRadius, center = center))


    /**
     * This Oval, recalculated as a Bézier path
     */
    private fun toBezierPath(): BezierPath = BezierPath(listOf(
            topRightBezierSegment,
            bottomRightBezierSegment,
            bottomLeftBezierSegment,
            topLeftBezierSegment
    ))


    /**
     * This Oval, cached as a Bézier path
     */
    override val bezierPathValue: BezierPath by lazy { toBezierPath() }

    /**
     * The top-right segment of this Oval as a Bézier path
     */
    private val topRightBezierSegment: CubicBezierPathSegment by lazy {
        CubicBezierPathSegment.ellipticPerpendicularCurve(start = boundingRect.midXminY, end = boundingRect.maxXmidY, clockwise = true)
    }

    /**
     * The bottom-right segment of this Oval as a Bézier path
     */
    private val bottomRightBezierSegment: CubicBezierPathSegment by lazy {
        CubicBezierPathSegment.ellipticPerpendicularCurve(start = boundingRect.maxXmidY, end = boundingRect.midXmaxY, clockwise = true)
    }

    /**
     * The bottom-left segment of this Oval as a Bézier path
     */
    private val bottomLeftBezierSegment: CubicBezierPathSegment by lazy {
        CubicBezierPathSegment.ellipticPerpendicularCurve(start = boundingRect.midXmaxY, end = boundingRect.minXmidY, clockwise = true)
    }

    /**
     * The top-left segment of this Oval as a Bézier path
     */
    private val topLeftBezierSegment: CubicBezierPathSegment by lazy {
        CubicBezierPathSegment.ellipticPerpendicularCurve(start = boundingRect.minXmidY, end = boundingRect.midXminY, clockwise = true)
    }
}


fun CubicBezierPathSegment.Companion.ellipticPerpendicularCurve(start: FractionPoint, end: FractionPoint, clockwise: Boolean): CubicBezierPathSegment {
    val horizontalFirst = clockwise == (start.x < end.x == start.y < end.y)
    val xControlPoint = controlPointDistanceForCircularCurveBetween(start.x, end.x)
    val yControlPoint = controlPointDistanceForCircularCurveBetween(start.y, end.y)
    return if (horizontalFirst)
        CubicBezierPathSegment(
                start = start,
                startControlPoint = FractionPoint(x = xControlPoint, y = start.y),
                endControlPoint = FractionPoint(x = end.x, y = yControlPoint),
                end = end
        )
    else
        CubicBezierPathSegment(
                start = start,
                startControlPoint = FractionPoint(x = start.x, y = yControlPoint),
                endControlPoint = FractionPoint(x = xControlPoint, y = end.y),
                end = end
        )
}
