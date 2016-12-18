package org.bh.tools.ui.swing

import org.bh.tools.base.abstraction.BHFloat
import org.bh.tools.base.math.float32Value
import org.bh.tools.base.math.geometry.FloatLineSegment
import org.bh.tools.base.math.geometry.FloatPoint
import org.bh.tools.base.math.geometry.FloatRect
import org.bh.tools.base.math.geometry.Point
import java.awt.Rectangle
import java.awt.Shape
import java.awt.geom.AffineTransform
import java.awt.geom.PathIterator
import java.awt.geom.PathIterator.*
import java.awt.geom.Point2D
import java.awt.geom.Rectangle2D

/**
 * Converts this fractionally-measured rectangle into an AWT shape
 *
 * @author Kyli Rouge
 * @since 2016-12-11
 */
val FloatRect.awtShapeValue: Shape get() = this.awtValue

val FloatLineSegment.awtShapeValue: Shape get() = object : Shape {

    private val line get() = this@awtShapeValue


    override fun contains(x: Double, y: Double): Boolean = contains(FloatPoint(x, y))


    override fun contains(p: Point2D?): Boolean = p != null && contains(FloatPoint(p))


    fun contains(point: Point<BHFloat>): Boolean = line.contains(point)


    override fun contains(x: Double, y: Double, width: Double, height: Double): Boolean = contains(FloatRect(x, y, width, height))


    override fun contains(r: Rectangle2D?): Boolean = r != null && contains(FloatRect(r))


    fun contains(rect: FloatRect): Boolean = rect.height == 0.0 && rect.width == 0.0 && contains(rect.origin)


    override fun getBounds2D(): Rectangle2D = line.bounds.awtValue

    // TODO: Test to ensure this works as expected
    override fun getPathIterator(at: AffineTransform?): PathIterator = object : PathIterator {

        private val transformedLine = this@awtShapeValue.transformed(at)
        private var isEndPoint = false

        override fun next() {
            isEndPoint = true
        }

        override fun getWindingRule() = WIND_NON_ZERO

        override fun currentSegment(coords: FloatArray?): Int {
            if (coords != null) {
                coords[0] = (if (isEndPoint) end else start).x.float32Value
                coords[1] = (if (isEndPoint) end else start).y.float32Value
            }
            return if (isEndPoint) SEG_LINETO else SEG_MOVETO
        }

        override fun currentSegment(coords: DoubleArray?): Int {
            if (coords != null) {
                coords[0] = (if (isEndPoint) end else start).x
                coords[1] = (if (isEndPoint) end else start).y
            }
            return if (isEndPoint) SEG_LINETO else SEG_MOVETO
        }

        override fun isDone(): Boolean {
            return isEndPoint // TODO: Test to ensure this works as expected
        }
    }

    override fun getPathIterator(at: AffineTransform?, flatness: Double): PathIterator {
        return getPathIterator(at, flatness)
    }

    override fun intersects(x: Double, y: Double, width: Double, height: Double): Boolean
            = intersects(FloatRect(x = x, y = y, width = width, height = height))

    override fun intersects(r: Rectangle2D?): Boolean
            = r != null && intersects(FloatRect(r))

    fun intersects(r: FloatRect): Boolean
            = line.intersects(FloatLineSegment(r.minXminY, r.maxXmaxY))
            || line.intersects(FloatLineSegment(r.maxXminY, r.minXmaxY))

    override fun getBounds(): Rectangle = line.bounds.awtValue.bounds

}
