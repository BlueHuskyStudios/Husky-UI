package org.bh.tools.ui.swing

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.math.fractionValue
import org.bh.tools.base.math.geometry.*
import org.bh.tools.base.math.int32Value
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

fun Graphics.drawLine(line: AnyLineSegment) = when (this) {
    is Graphics2D -> this.draw(line.fractionValue.awtShapeValue)
    else -> this.drawLine(line.start.x.int32Value, line.start.y.int32Value, line.end.x.int32Value, line.end.y.int32Value)
}


fun Graphics.drawLine(x1: Fraction, y1: Fraction, x2: Fraction, y2: Fraction)
= this.drawLine(FractionLineSegment(x1, y1, x2, y2))


fun Graphics.drawRect(rect: AnyRect) = when (this) {
    is Graphics2D -> this.draw(rect.fractionValue.awtShapeValue)
    else -> this.drawRect(rect.x.int32Value, rect.y.int32Value, rect.width.int32Value, rect.height.int32Value)
}


fun Graphics.fillOval(boundingRect: AnyRect) = when (this) {
    is Graphics2D -> this.fill(FractionOval(boundingRect = boundingRect.fractionValue).bezierPathValue.toAwtPath())
    else -> this.fillOval(boundingRect.x.int32Value, boundingRect.y.int32Value, boundingRect.width.int32Value, boundingRect.height.int32Value)
}


fun Graphics.fillCircle(radius: Number, center: AnyPoint) = when (this) {
    is Graphics2D -> this.fill(FractionOval(circularRadius = radius.fractionValue, center = center.fractionValue).bezierPathValue.toAwtPath())
    else -> this.fillOval(FractionOval.boundingRectAroundCircle(radius = radius.fractionValue, center = center.fractionValue))
}


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


/** Semantically describes text antialiasing in a limited scope */
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
