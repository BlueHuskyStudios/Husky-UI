@file:Suppress("unused")

package org.bh.tools.ui.generic

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.ui.generic.geometry.AxisOrientation
import org.bh.tools.ui.generic.geometry.AxisOrientation.euclidean
import org.bh.tools.ui.generic.geometry.AxisOrientation.flipped

/**
 * An anchor on a 2D view. For instance, the top-center, bottom-right, or center-center
 */
sealed class UIAnchor(
        /**
         * The distance on the X axis by which an element is offset from its parent, relative to this anchor point on
         * both the element and its parent
         */
        val xOffset: Fraction,

        /**
         * The distance on the Y axis by which an element is offset from its parent, relative to this anchor point on
         * both the element and its parent
         */
        val yOffset: Fraction) {

    /** The lowest X and Y values; the [origin] */
    class minXminY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)
    class midXminY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)
    class maxXminY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)

    class minXmidY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)
    class midXmidY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)
    class maxXmidY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)

    class minXmaxY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)
    class midXmaxY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)
    /** The highest X and Y values */
    class maxXmaxY(xOffset: Fraction = 0.0, yOffset: Fraction = 0.0): UIAnchor(xOffset, yOffset)

/* FIXME: Crashes the Kotlin compiler
    /**
     * Moves the given rect to a new position within the given frame, using this anchor as a reference point for both
     * rectangles, and offsetting from that reference point
     */
    fun reposition(rect: FractionRect, within: FractionRect): FractionRect {
        @Suppress("UnnecessaryVariable")
        val frame = within
        return when (this) {
            is minXminY -> rect.copy(newX = frame.minX + xOffset, newY = frame.minY + yOffset) // TODO: Test
            is midXminY -> TODO()
            is maxXminY -> TODO()
            is minXmidY -> TODO()
            is midXmidY -> TODO()
            is maxXmidY -> TODO()
            is minXmaxY -> TODO()
            is midXmaxY -> TODO()
            is maxXmaxY -> rect.copy(newOrigin = frame.maxXmaxY)
                    .offset(xOffset - rect.width.clampToPositive, yOffset - rect.height.clampToPositive)
        }
    }
*/


    companion object {

        /**
         * The center point on a plane
         */
        val centerCenter = midXmidY(0.0, 0.0)


        @JvmStatic
        fun topLeft(y: AxisOrientation = euclidean,
                    x: AxisOrientation = euclidean,
                    xOffset: Fraction = 0.0,
                    yOffset: Fraction = 0.0
        ): UIAnchor = when (y) {
            euclidean -> when (x) {
                euclidean -> minXmaxY(xOffset = xOffset, yOffset = yOffset)
                flipped -> maxXmaxY(xOffset = xOffset, yOffset = yOffset)
            }
            flipped -> when (x) {
                euclidean -> minXminY(xOffset = xOffset, yOffset = yOffset)
                flipped -> maxXminY(xOffset = xOffset, yOffset = yOffset)
            }
        }


        @JvmStatic
        fun topRight(y: AxisOrientation = euclidean,
                     x: AxisOrientation = euclidean,
                     xOffset: Fraction = 0.0,
                     yOffset: Fraction = 0.0
        ): UIAnchor = when (y) {
            euclidean -> when (x) {
                euclidean -> maxXmaxY(xOffset = xOffset, yOffset = yOffset)
                flipped -> minXmaxY(xOffset = xOffset, yOffset = yOffset)
            }
            flipped -> when (x) {
                euclidean -> maxXminY(xOffset = xOffset, yOffset = yOffset)
                flipped -> minXminY(xOffset = xOffset, yOffset = yOffset)
            }
        }


        @JvmStatic
        fun bottomLeft(y: AxisOrientation = euclidean,
                       x: AxisOrientation = euclidean,
                       xOffset: Fraction = 0.0,
                       yOffset: Fraction = 0.0
        ): UIAnchor = when (y) {
            euclidean -> when (x) {
                euclidean -> minXminY(xOffset = xOffset, yOffset = yOffset)
                flipped -> maxXminY(xOffset = xOffset, yOffset = yOffset)
            }
            flipped -> when (x) {
                euclidean -> minXmaxY(xOffset = xOffset, yOffset = yOffset)
                flipped -> maxXmaxY(xOffset = xOffset, yOffset = yOffset)
            }
        }


        @JvmStatic
        fun bottomRight(y: AxisOrientation = euclidean,
                        x: AxisOrientation = euclidean,
                        xOffset: Fraction = 0.0,
                        yOffset: Fraction = 0.0
        ): UIAnchor = when (y) {
            euclidean -> when (x) {
                euclidean -> maxXminY(xOffset = xOffset, yOffset = yOffset)
                flipped -> minXminY(xOffset = xOffset, yOffset = yOffset)
            }
            flipped -> when (x) {
                euclidean -> maxXmaxY(xOffset = xOffset, yOffset = yOffset)
                flipped -> minXmaxY(xOffset = xOffset, yOffset = yOffset)
            }
        }


        /**
         * The lowest X and Y values; [minXminY]
         */
        @JvmStatic
        val origin = minXminY(0.0, 0.0)
    }
}

