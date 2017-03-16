@file:Suppress("unused")

package org.bh.tools.ui.swing

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.math.*
import org.bh.tools.base.math.geometry.*
import org.bh.tools.ui.generic.geometry.FractionOval
import org.bh.tools.ui.swing.geometry.toAwtPath
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints.*

/*
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * To help with the graphics
 *
 * @author Kyli Rouge
 * @since 2016-11-06
 */



typealias GraphicsContext = Graphics



/**
 * Draws the given text at the given `(x, y)` coordinates
 *
 * @param string The text to draw
 * @param x      The `x` coordinate of the baseline of the text
 * @param y      The `y` coordinate of the lead of the text
 */
fun Graphics.drawString(string: CharSequence, x: Fraction, y: Fraction) = when (this) {
    is Graphics2D -> this.drawString(string.toString(), x.float32Value, y.float32Value)
    else -> this.drawString(string.toString(), x.int32Value, y.int32Value)
}


/**
 * Draws the given text at the given `(x, y)` coordinates
 *
 * @param string The text to draw
 * @param point  The `(x, y)` coordinate of the baseline and lead of the text, respectively
 */
fun Graphics.drawString(string: CharSequence, point: FractionPoint) = drawString(string, point.x, point.y)


/**
 * Draws the given line segment
 */
fun Graphics.drawLine(line: AnyLineSegment) = when (this) {
    is Graphics2D -> this.draw(line.fractionValue.awtShapeValue)
    else -> this.drawLine(line.start.x.int32Value, line.start.y.int32Value, line.end.x.int32Value, line.end.y.int32Value)
}


/**
 * Draws a line segment from `(x1, y1)` to `(x2, y2)`
 *
 * @param x1 The `x` coordinate of the first point of the line segment
 * @param y1 The `y` coordinate of the first point of the line segment
 * @param x2 The `x` coordinate of the second point of the line segment
 * @param y2 The `y` coordinate of the second point of the line segment
 */
fun Graphics.drawLine(x1: Fraction, y1: Fraction, x2: Fraction, y2: Fraction)
    = this.drawLine(FractionLineSegment(x1, y1, x2, y2))


/**
 * Draws the stroke of the given rectangle
 */
fun Graphics.drawRect(rect: AnyRect) = when (this) {
    is Graphics2D -> this.draw(rect.fractionValue.awtShapeValue)
    else -> this.drawRect(rect.x.int32Value, rect.y.int32Value, rect.width.int32Value, rect.height.int32Value)
}


/**
 * Fills the given rectangle
 */
fun Graphics.fillRect(rect: AnyRect) = when (this) {
    is Graphics2D -> this.fill(rect.fractionValue.awtShapeValue)
    else -> this.fillRect(rect.x.int32Value, rect.y.int32Value, rect.width.int32Value, rect.height.int32Value)
}


/**
 * Draws the stroke around the rectangle whose origin is at `(x, y)` and whose size is `width × height`
 */
fun Graphics.drawRect(x: Fraction, y: Fraction, width: Fraction, height: Fraction) = when (this) {
    is Graphics2D -> this.draw(FractionRect(x, y, width, height).awtShapeValue)
    else -> this.drawRect(x.int32Value, y.int32Value, width.int32Value, height.int32Value)
}


/**
 * Fills the rectangle whose origin is at `(x, y)` and whose size is `width × height`
 */
fun Graphics.fillRect(x: Fraction, y: Fraction, width: Fraction, height: Fraction) = when (this) {
    is Graphics2D -> this.fill(FractionRect(x, y, width, height).awtShapeValue)
    else -> this.fillRect(x.int32Value, y.int32Value, width.int32Value, height.int32Value)
}


/**
 * Draws the stroke of an oval within the given rectangle
 */
fun Graphics.drawOval(boundingRect: AnyRect) = when (this) {
    is Graphics2D -> this.draw(FractionOval(boundingRect = boundingRect.fractionValue).bezierPathValue.toAwtPath())
    else -> this.drawOval(boundingRect.x.int32Value, boundingRect.y.int32Value, boundingRect.width.int32Value, boundingRect.height.int32Value)
}


/**
 * Fills an oval within the given rectangle
 */
fun Graphics.fillOval(boundingRect: AnyRect) = when (this) {
    is Graphics2D -> this.fill(FractionOval(boundingRect = boundingRect.fractionValue).bezierPathValue.toAwtPath())
    else -> this.fillOval(boundingRect.x.int32Value, boundingRect.y.int32Value, boundingRect.width.int32Value, boundingRect.height.int32Value)
}


/** Draws the stroke of the given oval */
fun Graphics.drawOval(oval: FractionOval) = when (this) {
    is Graphics2D -> this.draw(oval.bezierPathValue.toAwtPath())
    else -> this.drawOval(oval.boundingRect)
}


/** Fills in the given oval */
fun Graphics.fillOval(oval: FractionOval) = when (this) {
    is Graphics2D -> this.fill(oval.bezierPathValue.toAwtPath())
    else -> this.fillOval(oval.boundingRect)
}


/**
 * Draws the stroke of a circle with the given radius and center point
 *
 * @param radius The distance from the center to the edge of the circle
 * @param center The point at the center of the circle
 */
fun Graphics.drawCircle(radius: Number, center: AnyPoint) = drawOval(FractionOval(circularRadius = radius.fractionValue, center = center.fractionValue))


/**
 * Fills in a circle with the given radius and center point
 *
 * @param radius The distance from the center to the edge of the circle
 * @param center The point at the center of the circle
 */
fun Graphics.fillCircle(radius: Number, center: AnyPoint) = fillOval(FractionOval(circularRadius = radius.fractionValue, center = center.fractionValue))


/**
 * Set or get whether this graphics context has anti-aliasing of any sort enabled for non-text rendering
 *
 * @see textAntiAlias for text anti-aliasing
 */
var Graphics.antiAlias: Boolean
    get() = when (this) {
        is Graphics2D -> this.getRenderingHint(KEY_ANTIALIASING) != VALUE_ANTIALIAS_OFF
        else -> false
    }
    set(newValue) {
        if (this is Graphics2D) {
            this.setRenderingHint(KEY_ANTIALIASING, if (newValue) VALUE_ANTIALIAS_ON else VALUE_ANTIALIAS_OFF)
        } // else nothing to do
    }


/**
 * Set or get whether this graphics context has anti-aliasing of any sort enabled for text rendering
 *
 * @see antiAlias for non-text anti-aliasing
 */
var Graphics.textAntiAlias: TextAntiAliasApproach
    get() = when (this) {
        is Graphics2D -> TextAntiAliasApproach.fromAwtValue(this.getRenderingHint(KEY_TEXT_ANTIALIASING)) ?: TextAntiAliasApproach.none
        else -> TextAntiAliasApproach.systemDefault
    }
    set(newValue) {
        if (this is Graphics2D) {
            this.setRenderingHint(KEY_TEXT_ANTIALIASING, newValue.awtValue)
        } // else nothing to do
    }


/** Semantically describes text anti-aliasing in a limited scope */
enum class TextAntiAliasApproach(
        /** The version of this approach when interacting with Java AWT and Swing */
        val awtValue: Any
) {
    /** Do not anti-alias text */
    none(VALUE_TEXT_ANTIALIAS_OFF),

    /** Anti-aliasing without specifying what kind */
    generic(VALUE_TEXT_ANTIALIAS_ON),

    /** The system's default anti-aliasing */
    systemDefault(VALUE_TEXT_ANTIALIAS_DEFAULT),

    /** The font's default anti-aliasing */
    fontDefault(VALUE_TEXT_ANTIALIAS_GASP),

    /** Subpixel anti-aliasing for monitors which horizontally display red, green, and blue in vertical stripes in that order */
    horizontalRGBStripe(VALUE_TEXT_ANTIALIAS_LCD_HRGB),

    /** Subpixel anti-aliasing for monitors which horizontally display blue, green, and red in vertical stripes in that order */
    horizontalBGRStripe(VALUE_TEXT_ANTIALIAS_LCD_HBGR),

    /** Subpixel anti-aliasing for monitors which vertically display red, green, and blue in horizontal stripes in that order */
    verticalRGBStripe(VALUE_TEXT_ANTIALIAS_LCD_VRGB),

    /** Subpixel anti-aliasing for monitors which vertically display blue, green, and red in horizontal stripes in that order */
    verticalBGRStripe(VALUE_TEXT_ANTIALIAS_LCD_VBGR),
    ;



    companion object {
        /** Converts a Java AWT value into a [TextAntiAliasApproach], or `null` if that can't be done */
        fun fromAwtValue(awtValue: Any): TextAntiAliasApproach? = values().firstOrNull { it.awtValue == awtValue }
    }
}


//fun Graphics.setAntiAlias(newValue: Boolean) {
//    if (this is Graphics2D) {
//        this.renderingHints[KEY_ANTIALIASING] = if (newValue) VALUE_ANTIALIAS_ON else VALUE_ANTIALIAS_OFF
//    } // else nothing to do
//}
//
//
//fun Graphics.isAntiAliased(): Boolean {
//    return when (this) {
//        is Graphics2D -> this.renderingHints[KEY_ANTIALIASING] != VALUE_ANTIALIAS_OFF
//        else -> false
//    }
//}
