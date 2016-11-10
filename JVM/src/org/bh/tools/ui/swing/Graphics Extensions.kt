package org.bh.tools.ui.swing

import org.bh.tools.base.struct.coord.IntPoint
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

fun Graphics.drawLine(start: IntPoint, end: IntPoint)
        = drawLine(start.x, start.y, end.x, end.y)

var Graphics.antiAlias: Boolean
    get() {
        if (this is Graphics2D) {
            return this.renderingHints[KEY_ANTIALIASING] != VALUE_ANTIALIAS_OFF
        } else {
            return false
        }
    }
    set(newValue) {
        if (this is Graphics2D) {
            this.renderingHints[KEY_ANTIALIASING] = if (newValue) VALUE_ANTIALIAS_ON else VALUE_ANTIALIAS_OFF
        } // else nothing to do
    }
