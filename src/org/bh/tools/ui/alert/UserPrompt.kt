package org.bh.tools.ui.alert



/**
 * A cross-platform, semantic, and easy-to-use user prompt
 *
 * @author Ben Leggiero
 * @since 2017-03-01
 */
interface UserPrompt<ParentComponent> {
    fun showOptionDialog(
            parentComponent: ParentComponent? = null,
            title: String,
            message: String,
            messageType: HuskyOptionPaneMessageType = HuskyOptionPaneMessageType.plain,
            optionType: HuskyOptionPaneOptionType = HuskyOptionPaneOptionType.default,
            allOptions: List<AlertOption> = optionType.defaultAlertOptions,
            defaultOption: AlertOption? = null,
            onUserSelection: (selectedOption: AlertOption) -> Unit
    )
}



/**
 * The option types for a [UserPrompt]
 *
 * @author Ben Leggiero
 * @since 2017-03-01
 */
enum class HuskyOptionPaneOptionType {
    /** The default option type for this look-and-feel */
    default,

    /** The dialog is used to get an important, explicit yes/no answer from the user */
    yesNo,

    /** The dialog is used to get an unimportant, optional yes/no answer from the user  */
    yesNoCancel,

    /** The dialog is used to confirm that an action is going to occur, or to cancel the action  */
    okCancel,

    /** The dialog is used to confirm that an action is going to (or has already) occur, or to cancel the action  */
    ok,
    ;


    val defaultAlertOptions: List<AlertOption> get() = when (this) {
        default, ok -> listOf(AlertOption.ok)
        yesNo -> listOf(AlertOption.yes, AlertOption.no)
        yesNoCancel -> listOf(AlertOption.yes, AlertOption.no, AlertOption.cancel)
        okCancel -> listOf(AlertOption.ok, AlertOption.cancel)
    }
}



/**
 * The message types of a [UserPrompt]
 *
 * @author Ben Leggiero
 * @since 2017-03-01
 */
enum class HuskyOptionPaneMessageType {
    /** **Discouraged:** Indicates the message has no connotations whatsoever */
    plain,

    /** The message is providing additional information to the user. For instance, an operation has completed. */
    info,

    /** The message is asking the user a question. The dialog should have more than 1 option. */
    question,

    /** The message is warning the user. This could be about a hazardous potential action, or some bad state. */
    warning,

    /** The message is alerting the user of something severe, which has likely already happened. */
    error
}
