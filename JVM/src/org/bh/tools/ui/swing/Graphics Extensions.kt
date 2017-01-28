package org.bh.tools.ui.swing

import org.bh.tools.base.math.geometry.AnyLineSegment
import org.bh.tools.base.math.geometry.AnyRect
import org.bh.tools.base.math.geometry.fractionValue
import org.bh.tools.base.math.int32Value
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

fun Graphics.drawLine(line: AnyLineSegment) {
    when (this) {
        is Graphics2D -> this.draw(line.fractionValue.awtShapeValue)
        else -> this.drawLine(line.start.x.int32Value, line.start.y.int32Value, line.end.x.int32Value, line.end.y.int32Value)
    }
}

fun Graphics.drawRect(rect: AnyRect) {
    when (this) {
        is Graphics2D -> this.draw(rect.fractionValue.awtShapeValue)
        else -> this.drawRect(rect.x.int32Value, rect.y.int32Value, rect.width.int32Value, rect.height.int32Value)
    }
}

//var Graphics.antiAlias: Boolean
//    get() {
//        return when (this) {
//            is Graphics2D -> this.renderingHints[KEY_ANTIALIASING] != VALUE_ANTIALIAS_OFF
//            else -> false
//        }
//    }
//    set(newValue) {
//        if (this is Graphics2D) {
//            this.renderingHints[KEY_ANTIALIASING] = if (newValue) VALUE_ANTIALIAS_ON else VALUE_ANTIALIAS_OFF
//        } // else nothing to do
//    }

fun Graphics.setAntiAlias(newValue: Boolean) {
    if (this is Graphics2D) {
        this.renderingHints[KEY_ANTIALIASING] = if (newValue) VALUE_ANTIALIAS_ON else VALUE_ANTIALIAS_OFF
    } // else nothing to do
}

fun Graphics.isAntiAliased(): Boolean {
    return when (this) {
        is Graphics2D -> this.renderingHints[KEY_ANTIALIASING] != VALUE_ANTIALIAS_OFF
        else -> false
    }
}
