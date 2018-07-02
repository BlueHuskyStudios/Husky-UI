@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package org.bh.tools.ui.visualization

/**
 * Describes the way in which a color will be used, like as a foreground or background color
 *
 * @author Ben Leggiero
 * @since 2018-06-28
 */
class UIColorUsage(val name: String) {
    companion object {
        val foreground = UIColorUsage("foreground")
        val background = UIColorUsage("background")
        inline val text get() = foreground
    }
}