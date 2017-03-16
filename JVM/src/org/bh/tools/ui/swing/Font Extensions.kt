@file:Suppress("unused")

package org.bh.tools.ui.swing

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.func.tuple
import org.bh.tools.base.math.float32Value
import java.awt.Font
import java.awt.font.TextAttribute

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



/** Creates and returns a font which is the same as this one, but with the given weight */
fun Font.withWeight(newWeight: javafx.scene.text.FontWeight): Font = deriveFont(this.attributes + tuple(TextAttribute.WEIGHT, newWeight.awtValue))


/** Creates and returns a font which is the same as this one, but with the given weight */
fun Font.withWeight(newWeight: FontWeight): Font = deriveFont(this.attributes + tuple(TextAttribute.WEIGHT, newWeight.awtValue))


enum class FontWeight(
        /** This font weight as an HTML value (100 to 900) */
        val htmlValue: Integer,
        /** This font weight as an AWT value (0.0 to 3.0); does not necessarily correspond with named [TextAttribute]s */
        val awtValue: Float32,
        /** This font weight as a JavaFX value */
        val fxValue: javafx.scene.text.FontWeight) {
    /** The lightest of fonts; lighter than [ultralight] */
    thin(100, 0.25f, javafx.scene.text.FontWeight.THIN),
    /** Very light; almost the lightest available; lighter than [light] but heavier than [thin] */
    ultralight(200, 0.5f, javafx.scene.text.FontWeight.EXTRA_LIGHT),
    /** A light font; a bit lighter than [regular] */
    light(300, 0.75f, javafx.scene.text.FontWeight.LIGHT),
    /** The standard font weight */
    regular(400, 1.0f, javafx.scene.text.FontWeight.NORMAL),
    /** A bit heavier than [regular] */
    medium(500, 1.5f, javafx.scene.text.FontWeight.MEDIUM),
    /** Halfway between [medium] and [bold] */
    semibold(600, 1.75f, javafx.scene.text.FontWeight.SEMI_BOLD),
    /** The standard "bold" weight */
    bold(700, 2.0f, javafx.scene.text.FontWeight.BOLD),
    /** Heavier than [bold] */
    ultrabold(800, 2.5f, javafx.scene.text.FontWeight.EXTRA_BOLD),
    /** The heaviest of fonts; heavier than [ultrabold] */
    black(900, 2.75f, javafx.scene.text.FontWeight.BLACK);


    companion object {
        /** A syntactic alias for [ultralight] */
        inline val extralight get() = ultralight
        /** A syntactic alias for [regular] */
        inline val normal get() = regular
        /** A syntactic alias for [regular] */
        inline val book get() = regular
        /** A syntactic alias for [semibold] */
        inline val demibold get() = semibold
        /** A syntactic alias for [ultrabold] */
        inline val extrabold get() = ultrabold
        /** A syntactic alias for [black] */
        inline val heavy get() = black
    }
}

/** Converts this FX Font Weight into an AWT font weight */
val javafx.scene.text.FontWeight.awtValue: Float32 get() = when(this) {
        javafx.scene.text.FontWeight.THIN -> 0.25f
        javafx.scene.text.FontWeight.EXTRA_LIGHT -> TextAttribute.WEIGHT_EXTRA_LIGHT
        javafx.scene.text.FontWeight.LIGHT -> TextAttribute.WEIGHT_LIGHT
        javafx.scene.text.FontWeight.NORMAL -> TextAttribute.WEIGHT_REGULAR
        javafx.scene.text.FontWeight.MEDIUM -> TextAttribute.WEIGHT_MEDIUM
        javafx.scene.text.FontWeight.SEMI_BOLD -> TextAttribute.WEIGHT_DEMIBOLD
        javafx.scene.text.FontWeight.BOLD -> TextAttribute.WEIGHT_BOLD
        javafx.scene.text.FontWeight.EXTRA_BOLD -> TextAttribute.WEIGHT_EXTRABOLD
        javafx.scene.text.FontWeight.BLACK -> TextAttribute.WEIGHT_ULTRABOLD
    }
