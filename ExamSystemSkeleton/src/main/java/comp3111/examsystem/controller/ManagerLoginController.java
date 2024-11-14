package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerLoginController implements Initializable {
    public static class Teacher{

    }
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code, if needed
    }

    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        // Simple validation for demonstration
        if (isValidLogin(username, password)) {
            showWelcomeMessage(username);

            // Load the Manager Main UI
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ManagerMainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + username + ", Welcome to HKUST Examination System");
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.show();

            // Close the current login window
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        } else {
            showErrorMessage("Invalid username or password.");
        }
    }

    private boolean isValidLogin(String username, String password) {
        // Replace this with actual login logic (e.g., checking against a database)
        return "manager".equals(username) && "password123".equals(password);
    }

    private void showWelcomeMessage(String username) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Successful");
        alert.setHeaderText(null);
        alert.setContentText("Hi " + username + ", Welcome to HKUST Examination System!");
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}