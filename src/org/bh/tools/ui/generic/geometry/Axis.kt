@file:Suppress("unused")

package org.bh.tools.ui.generic.geometry

import org.bh.tools.base.shim.*
import org.bh.tools.ui.generic.geometry.AxisOrientation.*



/**
 * A mathematical axis, like X and Y in 2D graphics
 */
interface Axis {
    val javaSwingOrientation: AxisOrientation
}



/**
 * A 1-dimensional axis, often called a "number line".
 */
enum class Axis1D(override val javaSwingOrientation: AxisOrientation = euclidean): Axis {
    /**
     * The only axis in 1 dimension
     */
    x;
}



/**
 * A 2-dimensional axis, often called a plane.
 */
enum class Axis2D(override val javaSwingOrientation: AxisOrientation = euclidean): Axis {
    /**
     * The horizontal axis
     */
    x,

    /**
     * The vertical axis
     */
    y(javaSwingOrientation = flipped);
}



enum class Axis3D(override val javaSwingOrientation: AxisOrientation = euclidean): Axis {
    /**
     * The horizontal axis
     */
    x,

    /**
     * The vertical axis
     */
    y,

    /**
     * The depth axis
     */
    z;
}



/**
 * The orientation of an [Axis]
 */
enum class AxisOrientation(val isFlipped: Boolean) {
    /**
     * The standard Euclidean orientation, where Y increases upward and X increases rightward.
     *
     * This is how you graph things in math, so that (0,0) is in the bottom-left.
     */
    euclidean(isFlipped = false),

    /**
     * The flipped form of the Euclidean orientation, where Y increases downward and X increases leftward.
     *
     * Traditionally, computers have euclidean X and flipped Y, so that (0,0) is in the top-left.
     */
    flipped(isFlipped = true);



    companion object {
        /**
         * The default orientation; [euclidean]
         */
        @JvmStatic
        val default = euclidean

        /**
         * The inverted orientation; [flipped]
         */
        @JvmStatic
        val inverted = flipped
    }
}
