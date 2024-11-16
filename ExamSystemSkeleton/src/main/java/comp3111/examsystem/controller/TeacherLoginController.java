package comp3111.examsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TeacherLoginController implements Initializable {



    public static class Teacher extends Entity{
        private String username;
        private String name;
        private String gender;
        private int age;
        private String position;
        private String department;
        private String password;

        //constructor
        public Teacher(String username, String name, String gender, int age, String position, String department, String password) {
            this.username = username;
            this.name = name;
            this.gender = gender;
            this.age = age;
            this.position = position;
            this.department = department;
            this.password = password;
        }

        // Getters and setters
        public String getUsername() {
            return username;
        }
        public String getName() {
            return name;
        }
        public String getGender() {
            return gender;
        }
        public int getAge() {
            return age;
        }
        public String getPosition() {
            return position;
        }
        public String getDepartment() {
            return department;
        }
        public String getPassword() {
            return password;
        }
    }

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

    public void initialize(URL location, ResourceBundle resources) {

    }
    public TeacherLoginController() {
        Database<Teacher> teacherDatabase = new Database<>(Teacher.class); // Initialize the database
    }
    // Method to register a new teacher (to be called in your register method)
    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        // Assume we have an validation method
        if (ValidLogin(username, password)) {
            showWelcomeMessage(username);

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
            showErrorMessage("Invalid username or password.");
        }

    }

    private boolean ValidLogin(String username, String password){
        for (Teacher teacher : TeacherDatabase.getTeachers()) {
            if (teacher.getUsername().equals(username) && teacher.getPassword().equals(password)) {
                return true; // Valid login found
            }
        }
        return false; // No matching credentials found
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
        String gender = genderCombo.getValue();
        int age = Integer.parseInt(ageTxt.getText());
        String position = PositionCombo.getValue();
        String department = departmentTxt.getText();
        String password = passwordTxt.getText();
        String passwordConfirm = passwordconfirmTxt.getText();

        // Basic validation (you can expand this as needed)
        if (username.isEmpty() || name.isEmpty() || gender == null || age < 0 || position == null || department.isEmpty() || password.isEmpty() || !password.equals(passwordConfirm)) {
            // Show an error message (you can use an Alert dialog for this)
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
            return;
        }

        // Create a new Teacher object
        Teacher newTeacher = new Teacher(username, name, gender, age, position, department, password);
        TeacherDatabase.addTeacher(newTeacher); // Add to the list of teachers
        // Here you can add the newTeacher to a list or save it to a database, etc.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register Successful");
        alert.setHeaderText(null);
        alert.setContentText("Teacher registered: " + newTeacher.getName());
        alert.showAndWait();
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }
}

