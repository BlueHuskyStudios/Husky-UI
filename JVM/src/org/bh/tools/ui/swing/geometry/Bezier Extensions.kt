@file:Suppress("unused")

package org.bh.tools.ui.swing.geometry

import org.bh.tools.base.collections.extensions.first
import org.bh.tools.base.math.geometry.BezierPath
import org.bh.tools.base.math.geometry.Point
import java.awt.geom.Path2D
import java.awt.geom.Point2D

/*
 * @author Kyli Rouge
 * @since 2017-01-29
 */



fun BezierPath.toAwtPath(): Path2D.Double {
    val path = Path2D.Double(Path2D.WIND_NON_ZERO, 4)
    path.moveTo(segments.first.start)
    segments.forEach { currentSegment ->
        path.curveTo(
                startControlPoint = currentSegment.nonNullStartControlPoint,
                endControlPoint = currentSegment.nonNullEndControlPoint,
                end = currentSegment.end
        )
    }
    return path
}


fun Path2D.moveTo(point: Point2D) {
    moveTo(point.x, point.y)
}


fun Path2D.moveTo(point: Point<Double>) {
    moveTo(point.x, point.y)
}


fun Path2D.curveTo(startControlPoint: Point<Double>, endControlPoint: Point<Double>, end: Point<Double>) {
    curveTo(startControlPoint.x, startControlPoint.y,
            endControlPoint.x, endControlPoint.y,
            end.x, end.y)
}
