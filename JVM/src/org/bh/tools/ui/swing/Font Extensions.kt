package org.bh.tools.ui.swing

import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.math.float32Value
import java.awt.Font

/*
 * Makes AWT fonts easier to use
 *
 * @author Ben Leggiero
 * @since 2017-03-10
 */

/** Creates and returns a font which is the same as this one, but with the given size */
fun Font.withSize(newSize: Fraction): Font = deriveFont(newSize.float32Value)

/** Creates and returns a font which is the same as this one, but with the given size */
fun Font.withSize(newSize: Integer): Font = deriveFont(newSize.float32Value)
