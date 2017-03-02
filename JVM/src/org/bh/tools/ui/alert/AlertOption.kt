package org.bh.tools.ui.alert

sealed class AlertOption(val displayName: String) {

    /** Closes the alert without selecting anything. This should likely be treated as either [cancel] or [no]. */
    object close : AlertOption("Close")

    /** A `Yes` option on an alert, usually paired with a [no] */
    object yes : AlertOption("Yes")

    /** A `No` option on an alert, usually paired with a [yes] */
    object no : AlertOption("No")

    /** A `Cancel` option on an alert, usually paired with an [ok]  */
    object cancel : AlertOption("Cancel")

    /** An `OK` option on an alert, sometimes paired with a [cancel] */
    object ok : AlertOption("OK")


    override fun toString(): String {
        return displayName
    }
}