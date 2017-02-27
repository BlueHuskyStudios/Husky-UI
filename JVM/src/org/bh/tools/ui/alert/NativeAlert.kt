package org.bh.tools.ui.alert

import com.sun.java.swing.plaf.windows.resources.windows
import javax.swing.JOptionPane

/**
 * @author Ben Leggiero
 * @since 2017-02-23
 */
object NativeAlert {
    fun showOptionlessConfirmation(title: String, detail: String, onUserConfirmation: () -> Unit) {
        when (OS.current) {
            is desktop -> showJOptionPane(
                    title = title,
                    message = "$title\r\n$detail",
                    messageType = JOptionPaneMessageType.info,
                    optionType = JOptionPaneOptionType.ok,
                    onUserConfirmation = onUserConfirmation)
        }
    }

    private fun showJOptionPane(title: String,
                                message: String,
                                messageType: JOptionPaneMessageType,
                                optionType: JOptionPaneOptionType,
                                allOptions: List<AlertOption>,
                                defaultOption: AlertOption,
                                onUserSelection: (selectedOption: AlertOption) -> Unit) {
        val selectedOptionIndex = JOptionPane.showOptionDialog(null, message, title, optionType.swingValue, messageType.swingValue, null, allOptions.toTypedArray(), defaultOption)
        val selectedOption = if (selectedOptionIndex < 0) AlertOption.close else allOptions[selectedOptionIndex]
        onUserSelection(selectedOption)
    }

}

enum class JOptionPaneOptionType(val swingValue: Int) {
    /** The default option type for this look-and-feel */
    default(-1),

    /** The dialog is used to get an important, explicit yes/no answer from the user */
    yesNo(0),

    /** The dialog is used to get an unimportant, optional yes/no answer from the user  */
    yesNoCancel(1),

    /** The dialog is used to confirm that an action is going to occur, or to cancel the action  */
    okCancel(2),

    /** The dialog is used to confirm that an action is going to (or has already) occur, or to cancel the action  */
    ok(2),
}

sealed class AlertOption(val displayName: String) {

    /** Closes the alert without selecting anything. This should likely be treated as either [cancel] or [no]. */
    object close: AlertOption("Close")

    /** A `Yes` option on an alert, usually paired with a [no] */
    object yes: AlertOption("Yes")

    /** A `No` option on an alert, usually paired with a [yes] */
    object no: AlertOption("No")

    /** A `Cancel` option on an alert, usually paired with an [ok]  */
    object cancel: AlertOption("Cancel")

    /** An `OK` option on an alert, sometimes paired with a [cancel] */
    object ok: AlertOption("OK")


    override fun toString(): String {
        return displayName
    }
}

enum class JOptionPaneMessageType(val swingValue: Int) {
    plain(swingValue = JOptionPane.PLAIN_MESSAGE),
    info(swingValue = JOptionPane.INFORMATION_MESSAGE),
    question(swingValue = JOptionPane.QUESTION_MESSAGE),
    warning(swingValue = JOptionPane.WARNING_MESSAGE),
    error(swingValue = JOptionPane.ERROR_MESSAGE)
}
