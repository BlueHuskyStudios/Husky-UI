package org.bh.tools.ui.swing

import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.math.geometry.Size
import org.bh.tools.base.math.int32Value
import java.awt.image.BufferedImage

/*
 * Makes making images easier
 *
 * @author Ben Leggiero
 * @since 2017-02-17
 */

fun BufferedImage(size: Size<Number>, imageType: BufferedImageType): BufferedImage {
    return BufferedImage(size.width.int32Value, size.height.int32Value, imageType.awtValue)
}


enum class BufferedImageType(val awtValue: Int32) {
    rgb(BufferedImage.TYPE_INT_RGB),
    argb(BufferedImage.TYPE_INT_ARGB),
    argbPremultiplied(BufferedImage.TYPE_INT_ARGB_PRE),
}
