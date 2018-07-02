package org.bh.tools.ui.visualization

/**
 * Describes a color used in user interfaces
 *
 * TODO: Make this an `expect class` once multiplatform is no longer experimental
 *
 * @author Ben Leggiero
 * @since 2018-06-28
 */

abstract class UIColor<Native>(open val nativeValue: Native)
