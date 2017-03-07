@file:Suppress("unused")

package org.bh.tools.ui.swing

import org.bh.tools.base.abstraction.*
import org.bh.tools.base.math.*
import java.awt.Color
import java.awt.color.ColorSpace
import java.lang.StrictMath.abs
import java.util.logging.Logger


/*
 * To make colors more fun!
 *
 * @author Ben Leggiero
 * @since 2017-02-20
 */

/**
 * Returns A version of this color with a new alpha component.
 * Note that only the following color spaces are supported:
 *   * [RGB(A)][ColorSpace.TYPE_RGB]
 *   * [HLS(A)][ColorSpace.TYPE_HLS]
 *   * [HSV(A)][ColorSpace.TYPE_HSV]
 *   * [G(A)][ColorSpace.TYPE_GRAY]
 */
fun java.awt.Color.withAlphaComponent(newAlphaComponent: Fraction): java.awt.Color {
    return when (this.colorSpace.type) {
        ColorSpace.TYPE_RGB -> awtColorFromRGBA(redFraction, greenFraction, blueFraction, newAlphaComponent)
        ColorSpace.TYPE_HLS -> awtColorFromHLSA(redFraction, greenFraction, blueFraction, newAlphaComponent)
        ColorSpace.TYPE_HSV -> awtColorFromHSVA(redFraction, greenFraction, blueFraction, newAlphaComponent)
        ColorSpace.TYPE_GRAY -> awtColorFromGray(grayFraction, newAlphaComponent)
        else -> {
            Logger.getGlobal().severe{"Unsupported color space: $colorSpace"}
            return java.awt.Color(red, green, blue, (newAlphaComponent * 255).roundedInt32Value)
        }
    }
}


fun awtColorFromRGBA(rgbaComponents: RGBAComponents): java.awt.Color
        = awtColorFromRGBA(
        red = rgbaComponents.red,
        green = rgbaComponents.green,
        blue = rgbaComponents.blue,
        alpha = rgbaComponents.alpha)


fun awtColorFromRGBA(red: Fraction, green: Fraction, blue: Fraction, alpha: Fraction): java.awt.Color
        = awtColor(
        colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB),
        components = fractionArrayOf(red, green, blue),
        alpha = alpha)


fun awtColorFromGray(gray: Fraction, alpha: Fraction): java.awt.Color
        = awtColorFromRGBA(
        red = gray,
        green = gray,
        blue = gray,
        alpha = alpha)



//fun awtColorFromHLSA(hue: Fraction, lightness: Fraction, saturation: Fraction, alpha: Fraction): java.awt.Color {
//    val q: Fraction
//    val p: Fraction
//    val red: Fraction
//    val green: Fraction
//    val blue: Fraction
//
//    if (saturation == 0.0) {
//        blue = lightness
//        green = blue
//        red = green // achromatic
//    } else {
//        q = if (lightness < 0.5) lightness * (1 + saturation) else lightness + saturation - lightness * saturation
//        p = 2 * lightness - q
//        red = hue2rgb(p, q, hue + 1.0f / 3)
//        green = hue2rgb(p, q, hue)
//        blue = hue2rgb(p, q, hue - 1.0f / 3)
//    }
//    return awtColorFromRGBA(red, green, blue, alpha)
//}
//
//fun hue2rgb(p: Fraction, q: Fraction, t: Fraction): Fraction {
//    val adjustedT =
//            if (t < 0) t + 1f
//            else if (t > 1) t - 1f
//            else t
//    return when (adjustedT) {
//        in (0.0..(1.0 / 6.0)) -> p + (q - p) * 6f * adjustedT
//        in (0.0..(1.0 / 2.0)) -> q
//        in (0.0..(2.0 / 3.0)) -> p + (q - p) * (2 / 3 - adjustedT) * 6f
//        else -> p
//    }
//}

fun awtColorFromHLSA(hue: Fraction, saturation: Fraction, lightness: Fraction, alpha: Fraction): java.awt.Color {
    return awtColorFromRGBA(hslaToRgba(hue, saturation, lightness, alpha))
}


/**
 * Converts HSLA (Hue, Saturation, Lightness, and Alpha) to RGBA (Red, Green, Blue, and Alpha).
 *
 * This is based on the algorithm described in https://en.wikipedia.org/wiki/HSL_and_HSV#Converting_to_RGB
 *
 * @param hue The hue of the color, from `0` inclusive to `360` exclusive. Values outside this range are modulated back.
 * @param saturation The saturation of the color, from `0` to `1`, inclusive. Values outside this range indicate extreme de- or hyper-saturation.
 * @param lightness  The lightness of the color
 */
fun hslaToRgba(hue: Fraction?, saturation: Fraction, lightness: Fraction, alpha: Fraction): RGBAComponents {
    // based on algorithm from http://en.wikipedia.org/wiki/HSL_and_HSV#Converting_to_RGB

    if (hue == null) {
        return RGBAComponents(0.0, 0.0, 0.0, alpha)
    }

    val chroma = (1 - abs((2 * lightness) - 1)) * saturation

    val huePrime = (hue % 360) / 60
    val x = chroma * (1 - abs((huePrime % 2) - 1)) // "x" because there's no short way of saying "the second-largest component of this color"
    val unadjustedRgb = when (huePrime) {
        // null -> already taken care of
        in (0.0..1.0) -> RGBAComponents(red = chroma, green = x,      blue = 0.0,    alpha = alpha)
        in (1.0..2.0) -> RGBAComponents(red = x,      green = chroma, blue = 0.0,    alpha = alpha)
        in (2.0..3.0) -> RGBAComponents(red = 0.0,    green = chroma, blue = x,      alpha = alpha)
        in (3.0..4.0) -> RGBAComponents(red = 0.0,    green = x,      blue = chroma, alpha = alpha)
        in (4.0..5.0) -> RGBAComponents(red = x,      green = 0.0,    blue = chroma, alpha = alpha)
        in (5.0..6.0) -> RGBAComponents(red = chroma, green = 0.0,    blue = x,      alpha = alpha)
        else -> RGBAComponents(0.0, 0.0, 0.0, alpha)
    }

    val adjustmentForLightness = lightness - (chroma / 2)

    val adjustedRGB = RGBAComponents(
            red = unadjustedRgb.red + adjustmentForLightness,
            green = unadjustedRgb.green + adjustmentForLightness,
            blue = unadjustedRgb.blue + adjustmentForLightness,
            alpha = unadjustedRgb.alpha
    )

    return adjustedRGB
}

fun hslaToRgba(hslaComponents: HSLAComponents): RGBAComponents {
    return hslaToRgba(
            hslaComponents.hue,
            hslaComponents.saturation,
            hslaComponents.lightness,
            hslaComponents.alpha)
}


fun awtColorFromHSVA(hue: Fraction, saturation: Fraction, value: Fraction, alpha: Fraction): java.awt.Color {
    return java.awt.Color.getHSBColor(hue.float32Value, saturation.float32Value, value.float32Value)
}


fun awtColor(colorSpace: ColorSpace, components: FractionArray, alpha: Fraction): java.awt.Color {
    return java.awt.Color(colorSpace, components.float32Value, alpha.float32Value)
}


fun awtColorFromHex(string: String): Color {
    val bytes: List<Int>
    val byteMatches = hexBytesRegex.findAll(string)
    if (byteMatches.count() >= 3) {
        bytes = byteMatches.map { it.value.toInt(0x10) }.toList()
    } else {
        val nibbleMatches = hexNibblesRegex.findAll(string)
        if (nibbleMatches.count() >= 3) {
            bytes = nibbleMatches.map { it.value.toInt(0x10) * 0x10 }.toList()
        } else {
            return Color.black
        }
    }

    val red = bytes[0]
    val green = bytes[1]
    val blue = bytes[2]
    val alpha = bytes.getOrNull(3) ?: 0xFF
    return Color(red, green, blue, alpha)
}


private val hexBytesRegex = "([0-9A-F]{2})".toRegex(RegexOption.IGNORE_CASE)
private val hexNibblesRegex = "([0-9A-F])".toRegex(RegexOption.IGNORE_CASE)


val java.awt.Color.rgbaComponents: RGBAComponents get() {
    val components = getComponents(ColorSpace.getInstance(ColorSpace.CS_sRGB), null)
    return RGBAComponents(
            red = components[0],
            green = components[1],
            blue = components[2],
            alpha = components[3]
    )
}


val java.awt.Color.grayComponents: GrayComponents get() {
    val components = getComponents(ColorSpace.getInstance(ColorSpace.CS_GRAY), null)
    return GrayComponents(
            gray = components[0],
            alpha = components[1]
    )
}


/**
 * The red component of this color, as a fraction where `0.0` is no red and `1.0` is completely red. Values over `1.0`
 * is used for extended color spaces like Display P3.
 */
val java.awt.Color.redFraction: Fraction get() {
    return rgbaComponents.red
}


/**
 * The green component of this color, as a fraction where `0.0` is no red and `1.0` is completely green. Values over
 * `1.0` is used for extended color spaces like Display P3.
 */
val java.awt.Color.greenFraction: Fraction get() {
    return rgbaComponents.green
}


/**
 * The blue component of this color, as a fraction where `0.0` is no red and `1.0` is completely blue. Values over `1.0`
 * is used for extended color spaces like Display P3.
 */
val java.awt.Color.blueFraction: Fraction get() {
    return rgbaComponents.blue
}


/**
 * The blue component of this color, as a fraction where `0.0` is no red and `1.0` is completely blue. Values over `1.0`
 * is used for extended color spaces like Display P3.
 */
val java.awt.Color.grayFraction: Fraction get() {
    return grayComponents.gray
}



interface ColorComponents {
    /** All components in an array of [Float32]s */
    val float32ArrayValue: Float32Array

    /** All components in an array of [Fraction]s */
    val fractionArrayValue: FractionArray
}



data class RGBAComponents( // TODO: Investigate speed consequences of using Fractions over Float32s
        val red: Fraction,
        val green: Fraction,
        val blue: Fraction,
        val alpha: Fraction
) : ColorComponents {
    constructor(
            red: Float32,
            green: Float32,
            blue: Float32,
            alpha: Float32
    ) : this(
            red = red.fractionValue,
            green = green.fractionValue,
            blue = blue.fractionValue,
            alpha = alpha.fractionValue
    )


    override val float32ArrayValue: FloatArray get() = float32ArrayOf(red.float32Value, green.float32Value, blue.float32Value, alpha.float32Value)
    override val fractionArrayValue: FractionArray get() = fractionArrayOf(red, green, blue, alpha)
}



data class GrayComponents(
        val gray: Fraction,
        val alpha: Fraction
) : ColorComponents {
    constructor(
            gray: Float32,
            alpha: Float32
    ) : this(
            gray = gray.fractionValue,
            alpha = alpha.fractionValue
    )


    override val float32ArrayValue: FloatArray get() = float32ArrayOf(gray.float32Value, alpha.float32Value)
    override val fractionArrayValue: FractionArray get() = fractionArrayOf(gray, alpha)
}



data class HSLAComponents( // TODO: Investigate speed consequences of using Fractions over Float32s
        val hue: Fraction,
        val saturation: Fraction,
        val lightness: Fraction,
        val alpha: Fraction
) : ColorComponents {
    constructor(
            hue: Float32,
            saturation: Float32,
            lightness: Float32,
            alpha: Float32
    ) : this(
            hue = hue.fractionValue,
            saturation = saturation.fractionValue,
            lightness = lightness.fractionValue,
            alpha = alpha.fractionValue
    )


    override val float32ArrayValue: FloatArray get() = float32ArrayOf(hue.float32Value, saturation.float32Value, lightness.float32Value, alpha.float32Value)
    override val fractionArrayValue: FractionArray get() = fractionArrayOf(hue, saturation, lightness, alpha)
}
