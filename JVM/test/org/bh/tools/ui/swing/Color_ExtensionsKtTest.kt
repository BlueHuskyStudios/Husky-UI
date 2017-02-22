package org.bh.tools.ui.swing


import org.bh.tools.base.abstraction.Fraction
import org.bh.tools.base.abstraction.Int32
import org.bh.tools.base.math.fractionValue
import org.bh.tools.base.strings.toString
import org.junit.Assert
import org.junit.Test
import java.util.logging.Logger


private val tolerance = 0.01


/**
 * @author Kyli Rouge
 * *
 * @since 2017-02-21 021.
 */
class Color_ExtensionsKtTest {

    val red50 = TestCase(name = "Red 50", rgb = rgb(255,235,238), hsl = hsl(351, 1.00, .96))
    val pink100 = TestCase(name = "Pink 100", rgb = rgb(248,187,208), hsl = hsl(339, .81, .85))
    val purple200 = TestCase(name = "Purple 200", rgb = rgb(206,147,216), hsl = hsl(291, .47, .71))
    val deepPurple300 = TestCase(name = "Deep Purple 300", rgb = rgb(149,117,205                                 + 20), hsl = hsl(262, .47, .63))
    val indigo400 = TestCase(name = "Indigo 400", rgb = rgb(92,107,192), hsl = hsl(231, .44, .56))
    val blue500 = TestCase(name = "Blue 500", rgb = rgb(33,150,243), hsl = hsl(207, .90, .54))
    val lightBlue600 = TestCase(name = "Light Blue 600", rgb = rgb(3,155,229), hsl = hsl(200, .97, .45))
    val cyan700 = TestCase(name = "Cyan 700", rgb = rgb(0,151,167), hsl = hsl(186, 1.00, .33))
    val teal800 = TestCase(name = "Teal 800", rgb = rgb(0,105,92), hsl = hsl(173, 1.00, .21))
    val green900 = TestCase(name = "Green 900", rgb = rgb(27,94,32), hsl = hsl(124, .55, .24))
    val lightGreenA100 = TestCase(name = "Light Green A100", rgb = rgb(204,255,144), hsl = hsl(88, 1.00, .78))
    val limeA200 = TestCase(name = "Lime A200", rgb = rgb(238,255,65), hsl = hsl(65, 1.00, .63))
    val yellowA400 = TestCase(name = "Yellow A400", rgb = rgb(255,234,0), hsl = hsl(55, 1.00, .50))
    val amberA700 = TestCase(name = "Amber A700", rgb = rgb(255,171,0), hsl = hsl(40, 1.00, .50))
    val orangeA400 = TestCase(name = "Orange A400", rgb = rgb(255,145,0), hsl = hsl(34, 1.00, .50))
    val deepOrangeA200 = TestCase(name = "Deep Orange A200", rgb = rgb(255,110,64), hsl = hsl(14, 1.00, .63))
    val brown900 = TestCase(name = "Brown 900", rgb = rgb(62,39,35), hsl = hsl(9, .28, .19))
    val grey800 = TestCase(name = "Grey 800", rgb = rgb(66,66,66), hsl = hsl(0, 0.0, .26))
    val blueGrey700 = TestCase(name = "Blue Grey 700", rgb = rgb(69,90,100), hsl = hsl(199, .18, .33))

    val allTestCases = setOf(
            red50,
            pink100,
            purple200,
            deepPurple300,
            indigo400,
            blue500,
            lightBlue600,
            cyan700,
            teal800,
            green900,
            lightGreenA100,
            limeA200,
            yellowA400,
            amberA700,
            orangeA400,
            deepOrangeA200,
            brown900,
            grey800,
            blueGrey700
    )

    @Test
    fun hslaToRgba() {
        allTestCases.forEach { (name, expectedRGB, hsl) ->
            Logger.getGlobal().info { "@Test hslaToRgba($name)..." }
            val actual = org.bh.tools.ui.swing.hslaToRgba(hsl)
            Logger.getGlobal().info { "\tGenerated ${actual.stringValue}, expected ${expectedRGB.stringValue}" }
            Assert.assertEquals("❌ Red value outside tolerance when converting $hsl",   expectedRGB.red,   actual.red,   tolerance)
            Assert.assertEquals("❌ Green value outside tolerance when converting $hsl", expectedRGB.green, actual.green, tolerance)
            Assert.assertEquals("❌ Blue value outside tolerance when converting $hsl",  expectedRGB.blue,  actual.blue,  tolerance)
            Assert.assertEquals("❌ Alpha value outside tolerance when converting $hsl", expectedRGB.alpha, actual.alpha, tolerance)
            Logger.getGlobal().info { "\t✅ Passed!" }
        }
    }
}


data class TestCase(val name: String, val rgb: RGBAComponents, val hsl: HSLAComponents)


fun rgba(red: Int32, green: Int32, blue: Int32, alpha: Fraction): RGBAComponents {
    return RGBAComponents(
            red = red.fractionValue / 255,
            green = green.fractionValue / 255,
            blue = blue.fractionValue / 255,
            alpha = alpha
    )
}


fun rgba(red: Int32, green: Int32, blue: Int32, alpha: Int32): RGBAComponents {
    return rgba(red, green, blue, alpha.fractionValue)
}


fun rgb(red: Int32, green: Int32, blue: Int32): RGBAComponents {
    return rgba(red, green, blue, 1)
}


fun hsl(hue: Int32, saturation: Fraction, lightness: Fraction): HSLAComponents {
    return HSLAComponents(
            hue = hue.fractionValue,
            saturation = saturation,
            lightness = lightness,
            alpha = 1.0
    )
}


fun hsl(hue: Int32, saturation: Int32, lightness: Int32): HSLAComponents {
    return hsl(
            hue = hue,
            saturation = saturation.fractionValue / 100.0,
            lightness = lightness.fractionValue / 100.0
    )
}


private val RGBAComponents.stringValue: String
    get() = "rgba(${red.toString(fractionDigits = 2)}, ${green.toString(fractionDigits = 2)}, ${blue.toString(fractionDigits = 2)}, ${alpha.toString(fractionDigits = 2)})"


private val HSLAComponents.stringValue: String
    get() = "hsla(${hue.toString(fractionDigits = 2)}, ${saturation.toString(fractionDigits = 2)}, ${lightness.toString(fractionDigits = 2)}, ${alpha.toString(fractionDigits = 2)})"
