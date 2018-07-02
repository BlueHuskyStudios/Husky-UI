@file:Suppress("unused")

package org.bh.tools.ui.visualization

/**
 * Describes the presentation of a UI element
 *
 * @author Ben Leggiero
 * @since 2018-06-28
 */
class UIPresentation(
        /** Whether or not the UI element can be interacted with */
        val isEnabled: Boolean,

        /**
         * Whether or not the UI element can be seen.
         * An invisible element might, but doesn't necessarily, imply that it cannot be interacted with.
         */
        val isVisible: Boolean,

        /**
         * The colors of this UI element. If one is not specified, a reasonable default should be used.
         */
        val colors: Map<UIColorUsage, UIColor<*>>
) {
    companion object {
        val default = UIPresentation(isEnabled = true,
                                     isVisible = true,
                                     colors = mapOf())
    }
}
