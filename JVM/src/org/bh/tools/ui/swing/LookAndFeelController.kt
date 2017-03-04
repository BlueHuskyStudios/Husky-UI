package org.bh.tools.ui.swing

import javax.swing.LookAndFeel
import javax.swing.UIManager
import kotlin.reflect.jvm.jvmName

/**
 * @author Ben Leggiero
 * @since 2017-03-04
 */
object LookAndFeelController {
    var currentApplicationLookAndFeel: CommonLookAndFeel
        get() = CommonLookAndFeel.from(currentSwingLookAndFeel)
        set(newValue) {
            if (newValue != currentApplicationLookAndFeel) {
                currentSwingLookAndFeelClassName = newValue.swingClassName
            }
        }


    var currentSwingLookAndFeelClassName: String
        get() = UIManager.getLookAndFeel()::class.jvmName
        set(newValue) {
            if (newValue != currentSwingLookAndFeelClassName) {
                UIManager.setLookAndFeel(newValue)
            }
        }


    var currentSwingLookAndFeel: LookAndFeel
        get() = UIManager.getLookAndFeel()
        set(newValue) {
            if (newValue != currentSwingLookAndFeel) {
                UIManager.setLookAndFeel(newValue)
            }
        }
}

enum class CommonLookAndFeel(val swingClassName: String) {
    /** A Swing Look-And-Feel guaranteed to work across platforms */
    crossPlatform(UIManager.getCrossPlatformLookAndFeelClassName()),
    /** A Swing Look-And-Feel guaranteed to look native on this OS */
    native(UIManager.getSystemLookAndFeelClassName());

    val swingValue: LookAndFeel get() = Class.forName(swingClassName).newInstance() as LookAndFeel

    companion object {
        /**
         * Attempts to find the [CommonLookAndFeel] that best matches the given [LookAndFeel]. If none is found,
         * [crossPlatform] is returned.
         */
        fun from(swingValue: LookAndFeel): CommonLookAndFeel {
            return values().firstOrNull { swingValue::class.jvmName == it.swingClassName } ?: crossPlatform
        }

    }
}
