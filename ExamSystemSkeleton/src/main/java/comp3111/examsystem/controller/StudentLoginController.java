package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
import comp3111.examsystem.tools.MsgSender;
import comp3111.examsystem.tools.Database;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentLoginController implements Initializable {
    private Database<Student> studentDatabase;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    private static final String ALLOWED_LOGIN_CHARS = "^[a-zA-Z0-9_]+$";
    private static final String ALLOWED_PASSWORD_CHARS = "^[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\}\\|;:'\",<.>/?]+$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialization code, if needed
        studentDatabase = new Database<>(Student.class);

        // Additional initialization code if required

    }

    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        List<Student> students = studentDatabase.queryByField("name", username);

        // Simple validation for demonstration
        if (isValidLogin(username, password) && students.get(0).getPassword().equals(password)) {
            MsgSender.showMsg("Login Successful");
            showWelcomeMessage(username);

            // Load the Manager Main UI
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentMainUI.fxml"));
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
            MsgSender.showMsg("Login Failed: Invalid username or password.");
        }
    }

    static boolean isValidLogin(String username, String password) {
        // Replace this with actual login logic (e.g., checking against a database)
        boolean username_flag = username.matches(ALLOWED_LOGIN_CHARS);
        boolean password_flag = username.matches(ALLOWED_PASSWORD_CHARS);

        return username_flag && password_flag;
    }


    private void showWelcomeMessage(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Successful");
        alert.setHeaderText(null);
        alert.setContentText("Hi " + username + ", Welcome to HKUST Examination System!");
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public void register() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentRegisterUI.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Student Registration");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage.show();

    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void enterinfo(ActionEvent actionEvent) {


          String username = usernameTxt.getText();
//        String name = nameTxt.getText();
//        String gender = genderCombo.getValue();
//        int age = Integer.parseInt(ageTxt.getText());
//        String position = PositionCombo.getValue();
//        String department = departmentTxt.getText();
//        String password = passwordTxt.getText();
//        String passwordConfirm = passwordconfirmTxt.getText();
//
//        // Basic validation (you can expand this as needed)
//        if (username.isEmpty() || name.isEmpty() || gender == null || age < 0 || position == null || department.isEmpty() || password.isEmpty() || !password.equals(passwordConfirm)) {
//            // Show an error message (you can use an Alert dialog for this)
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Register Failed");
//            alert.setHeaderText(null);
//            alert.setContentText("Please fill in all fields correctly.");
//            alert.showAndWait();
//            return;
//        }
//
//        // Create a new Teacher object
//        Student newStudent = new Teacher(username, name, gender, age, department, password);
//        StudentDatabase.addTeacher(newStudent); // Add to the list of teachers
//        // Here you can add the newStudent to a list or save it to a database, etc.
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Register Successful");
//        alert.setHeaderText(null);
//        alert.setContentText("Student registered: " + newStudent.getName());
//        alert.showAndWait();
//        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
//        stage.close();
    }

}
