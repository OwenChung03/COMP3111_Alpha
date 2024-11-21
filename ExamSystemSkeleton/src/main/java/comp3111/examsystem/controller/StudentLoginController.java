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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static comp3111.examsystem.tools.MsgSender.showMsg;

public class StudentLoginController implements Initializable {
    @FXML
    private TextField login_usernameTxt;  // TextField for student's username
    @FXML
    private TextField login_passwordTxt;      // TextField for student's name
    @FXML
    private TextField usernameTxt;  // TextField for student's username
    @FXML
    private TextField nameTxt;      // TextField for student's name
    @FXML
    private ComboBox<String> genderCombo;  // ComboBox for student's gender
    @FXML
    private TextField ageTxt;       // TextField for student's age
    @FXML
    private TextField departmentTxt;  // TextField for student's department
    @FXML
    private PasswordField passwordTxt;  // PasswordField for student's password
    @FXML
    private PasswordField passwordconfirmTxt;  // PasswordField for confirming student's password

    private Database<Student> studentDatabase;

    private static final String ALLOWED_LOGIN_CHARS = "^[a-zA-Z0-9_]+$";
    private static final String ALLOWED_PASSWORD_CHARS = "^[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\}\\|;:'\",<.>/?]+$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initialization code, if needed
        studentDatabase = new Database<>(Student.class);

        // Additional initialization code if required

    }

    // Static field to store the logged-in student
    private static Student loggedInStudent;

    // Getter for the logged-in student
    public static Student getLoggedInStudent() {
        return loggedInStudent;
    }

    @FXML
    public void login(ActionEvent e) {
        String username = login_usernameTxt.getText();
        String password = login_passwordTxt.getText();
        List<Student> students = studentDatabase.queryByField("username", username);

        if (students.isEmpty()) {
            showMsg("Error","Login Failed: No user found.");
            return;
        }

        // Simple validation for demonstration
        if (isValidLogin(username, password) && students.get(0).getPassword().equals(password)) {
            loggedInStudent = students.get(0);
            showMsg("Success","Login Successful");

            //showWelcomeMessage(username);

            // Load the Student Main UI
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
            //showErrorMessage("Invalid username or password.");
            showMsg("Error","Login Failed: Invalid username or password.");
        }
    }

    static boolean isValidLogin(String username, String password) {
        // Replace this with actual login logic (e.g., checking against a database)
        boolean username_flag = username.matches(ALLOWED_LOGIN_CHARS);
        boolean password_flag = password.matches(ALLOWED_PASSWORD_CHARS);

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
        String name = nameTxt.getText();
        String gender = genderCombo.getValue();
        String ageString = ageTxt.getText();
        String department = departmentTxt.getText();
        String password = passwordTxt.getText();
        String confirmPassword = passwordconfirmTxt.getText();


        if (username.isEmpty() || name.isEmpty() || gender == null || ageString.isEmpty() || department.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
            showMsg("Error","Error: All fields are required.");
            return;
        }

        try {
            int age = Integer.parseInt(ageString);   // Convert age from String to int
            if (age <= 0) {
                showMsg("Error","Error: Age must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showMsg("Error","Error: Please enter a valid age.");
            return;
        }

        // Validate that the passwords match
        if (!password.equals(confirmPassword)) {
            showMsg("Error","Error: Passwords do not match.");
            return;
        }

        // Check if the username already exists in the database
        if (!studentDatabase.queryByField("username", username).isEmpty()) {
            showMsg("Error","Error: Username already exists. Please choose a different one.");
            return;
        }

        // Create a new Student object
        Student newStudent = new Student(null, username,name, gender, ageString, department, password);

        // Add the new student to the database (write to file)
        studentDatabase.add(newStudent);


        // Show success message
        showMsg("Success","Student registered successfully!");

        // Close the registration window
        Stage stage = (Stage) usernameTxt.getScene().getWindow();
        stage.close();


    }

}
