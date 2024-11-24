package comp3111.examsystem.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
/**
 * Utility class for displaying message alerts and confirmation dialogs in a JavaFX application.
 * This class provides static methods for showing informational messages and confirmation prompts.
 */
public class MsgSender {
    /**
     * Displays an informational message dialog with the specified title and message.
     *
     * @param title the title of the message dialog
     * @param msg   the message to display in the dialog
     */
    static public void showMsg(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set(title);
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
    }
    /**
     * Displays a confirmation dialog with the specified title and message.
     * If the user confirms the action, the provided callback is executed.
     *
     * @param title   the title of the confirmation dialog
     * @param msg     the message to display in the dialog
     * @param callback a Runnable to execute if the user confirms the action
     */
    static public void showConfirm(String title, String msg, Runnable callback) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(msg);
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            callback.run();
        }
    }
}
