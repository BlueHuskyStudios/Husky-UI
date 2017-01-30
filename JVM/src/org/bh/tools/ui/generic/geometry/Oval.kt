@file:Suppress("unused")

package org.bh.tools.ui.generic.geometry

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.math.geometry.*
import org.bh.tools.ui.generic.geometry.FractionOval.Companion.controlPointDistanceForCircularCurveBetween

/**
 * An oval
 *
 * @author Kyli Rouge
 * @since 2017-01-27 027.
 */
interface Oval<NumberType: Number> {
    val boundingRect: ComputableRect<NumberType, ComputablePoint<NumberType>, ComputableSize<NumberType>>

    val bezierPathValue: BezierPath
    val bézierPathValue: BézierPath get() = bezierPathValue
}



class FractionOval(override val boundingRect: FractionRect): Oval<Fraction> {

    companion object {
        val controlPointPercentDistanceForCircularCurve: Fraction = (1.0 / 3.0) * (StrictMath.sqrt(7.0) - 1.0) // twice as precise as: (4.0/3.0)*(StrictMath.sqrt(2.0) - 1.0)

        fun controlPointDistanceForCircularCurveBetween(a: Fraction, b: Fraction): Fraction {
            return a + ((b - a) * controlPointPercentDistanceForCircularCurve)
        }
    }

    private fun toBezierPath(): BezierPath {
        return BezierPath(listOf(
                topRightBezierSegment,
                bottomRightBezierSegment,
                bottomLeftBezierSegment,
                topLeftBezierSegment
        ))
    }

    override val bezierPathValue: BezierPath by lazy { toBezierPath() }

    private val topRightBezierSegment: CubicBezierPathSegment by lazy {
        CubicBezierPathSegment.ellipticPerpendicularCurve(start = boundingRect.midXminY, end = boundingRect.maxXmidY)
    }
    private val bottomRightBezierSegment: CubicBezierPathSegment by lazy {
        CubicBezierPathSegment.ellipticPerpendicularCurve(start = boundingRect.maxXmidY, end = boundingRect.midXmaxY)
    }
    private val bottomLeftBezierSegment: CubicBezierPathSegment by lazy {
        CubicBezierPathSegment.ellipticPerpendicularCurve(start = boundingRect.midXmaxY, end = boundingRect.minXmidY)
    }
    private val topLeftBezierSegment: CubicBezierPathSegment by lazy {
        CubicBezierPathSegment.ellipticPerpendicularCurve(start = boundingRect.minXmidY, end = boundingRect.midXminY)
    }
}



fun CubicBezierPathSegment.Companion.ellipticPerpendicularCurve(start: FractionPoint, end: FractionPoint): CubicBezierPathSegment {
    return CubicBezierPathSegment(
            start = start,
            startControlPoint = FractionPoint(x = controlPointDistanceForCircularCurveBetween(start.x, end.x), y = start.y),
            endControlPoint = FractionPoint(x = end.x, y = controlPointDistanceForCircularCurveBetween(start.y, end.y)),
            end = end
    )
}
