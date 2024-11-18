package comp3111.examsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import static comp3111.examsystem.tools.MsgSender.showMsg;
import comp3111.examsystem.tools.Database;

import comp3111.examsystem.Entity.Teacher;

public class TeacherLoginController implements Initializable {

    private static final String ALLOWED_LOGIN_CHARS = "^[a-zA-Z0-9_]+$";
    private static final String ALLOWED_PASSWORD_CHARS = "^[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\}\\|;:'\",<.>/?]+$";
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    public TextField nameTxt;
    @FXML
    public ComboBox<String> genderCombo; // Ensure this matches the fx:id in FXML
    @FXML
    public TextField ageTxt;
    @FXML
    public ComboBox<String> PositionCombo;
    @FXML
    public TextField departmentTxt;
    @FXML
    public PasswordField passwordconfirmTxt;
    private Database<Teacher> TeacherDatabase;

    public void initialize(URL location, ResourceBundle resources) {
        TeacherDatabase = new Database<>(Teacher.class);
    }
    // Method to register a new teacher (to be called in your register method)
    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        List<Teacher> teachers = TeacherDatabase.queryByField("username", username);
        // Assume we have an validation method
        if (teachers.isEmpty()) {
            showMsg("Error","Login Failed: No user found.");
            return;
        }
        if (ValidLogin(username, password) && teachers.get(0).getPassword().equals(password)) {
            showMsg("Welcome","Login Successful");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherMainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + usernameTxt.getText() + ", Welcome to HKUST Examination System");
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            stage.show();
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        }
        else {
            showMsg("Error","Invalid username or password.");
        }

    }

    private boolean ValidLogin(String username, String password) {
        // Initialize the database
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
    public void register(ActionEvent e) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherRegisterUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Teacher Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    public void close(ActionEvent e) {
        // Get the current stage (window) and close it
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void enterinfo(ActionEvent e) {
        // Retrieve data from input fields
        String username = usernameTxt.getText();
        String name = nameTxt.getText();
        String age = ageTxt.getText();
        String gender = genderCombo.getValue();
        String position = PositionCombo.getValue();
        String department = departmentTxt.getText();
        String password = passwordTxt.getText();
        String passwordConfirm = passwordconfirmTxt.getText();

        // Basic validation (you can expand this as needed)
        if (username.isEmpty() || name.isEmpty() || gender == null || age.isEmpty() || position == null || department.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            // Show an error message (you can use an Alert dialog for this)
            showMsg("Error","Error: Please fill in all fields correctly.");
            return;
        }
        try {// Convert age from String to int
            int agenum = Integer.parseInt(ageTxt.getText());
            if (agenum <= 0) {
                showMsg("Error","Error: Age must be a positive number.");
                return;
            }
        } catch (NumberFormatException e1) {
            showMsg("Error","Error: Please enter a valid age.");
            return;
        }

        // Validate that the passwords match
        if (!password.equals(passwordConfirm)) {
            showMsg("Error","Error: Passwords do not match.");
            return;
        }
        if (!TeacherDatabase.queryByField("username", username).isEmpty()) {
            showMsg("Error","Error: Username already exists. Please choose a different one.");
            return;
        }

        // Create a new Teacher object
        Teacher newTeacher = new Teacher(username, name, gender, age, position, department, password);

        System.out.println(newTeacher);

        // Add the new student to the database (write to file)
        TeacherDatabase.add(newTeacher);

        System.out.println("Added");

        List<Teacher> allTeachers = TeacherDatabase.getAll();
        System.out.println("All teachers after registration:");
        for (Teacher teacher : allTeachers) {
            System.out.println(teacher);
        }

        // Show success message
        showMsg("Welcome","Teacher registered successfully!");

        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
