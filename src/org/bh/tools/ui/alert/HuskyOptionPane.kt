package org.bh.tools.ui.alert

import javax.swing.JComponent
import javax.swing.JOptionPane

/**
 * A wrapper around [JOptionPane], to make it more semantic and modern
 *
 * @author Ben Leggiero
 * @since 2017-03-01
 */
object HuskyOptionPane {
    inline fun showOptionDialog(
            parentComponent: JComponent? = null,
            title: String,
            message: String,
            messageType: JOptionPaneMessageType,
            optionType: JOptionPaneOptionType,
            allOptions: List<AlertOption>,
            defaultOption: AlertOption,
            onUserSelection: (selectedOption: AlertOption) -> Unit
    ) {
        val selectedOptionIndex = JOptionPane.showOptionDialog(
                parentComponent,
                message,
                title,
                optionType.swingValue,
                messageType.swingValue,
                null,
                allOptions.toTypedArray(),
                defaultOption
        )

        val selectedOption = if (selectedOptionIndex < 0) AlertOption.close else allOptions[selectedOptionIndex]
        onUserSelection(selectedOption)
    }
}



/**
 * A semantic wrapper around [JOptionPane]'s option types
 *
 * @author Ben Leggiero
 * @since 2017-03-01
 */
enum class JOptionPaneOptionType(val swingValue: Int) {
    /** The default option type for this look-and-feel */
    default(JOptionPane.DEFAULT_OPTION),

    /** The dialog is used to get an important, explicit yes/no answer from the user */
    yesNo(JOptionPane.YES_NO_OPTION),

    /** The dialog is used to get an unimportant, optional yes/no answer from the user  */
    yesNoCancel(JOptionPane.YES_NO_CANCEL_OPTION),

    /** The dialog is used to confirm that an action is going to occur, or to cancel the action  */
    okCancel(JOptionPane.OK_CANCEL_OPTION),

    /** The dialog is used to confirm that an action is going to (or has already) occur, or to cancel the action  */
    ok(JOptionPane.OK_CANCEL_OPTION),
}



/**
 * A semantic wrapper around [JOptionPane]'s message types
 *
 * @author Ben Leggiero
 * @since 2017-03-01
 */
enum class JOptionPaneMessageType(val swingValue: Int) {
    /** **Discouraged:** Indicates the message has no connotations whatsoever */
    plain(swingValue = JOptionPane.PLAIN_MESSAGE),

    /** The message is providing additional information to the user. For instance, an operation has completed. */
    info(swingValue = JOptionPane.INFORMATION_MESSAGE),

    /** The message is asking the user a question. The dialog should have more than 1 option. */
    question(swingValue = JOptionPane.QUESTION_MESSAGE),

    /** The message is warning the user. This could be about a hazardous potential action, or some bad state. */
    warning(swingValue = JOptionPane.WARNING_MESSAGE),

    /** The message is alerting the user of something severe, which has likely already happened. */
    error(swingValue = JOptionPane.ERROR_MESSAGE)
}
