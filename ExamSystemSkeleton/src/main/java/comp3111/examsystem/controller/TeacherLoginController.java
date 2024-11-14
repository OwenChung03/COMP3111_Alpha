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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TeacherLoginController implements Initializable {

    public static class Teacher{
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
    public TextField genderTxt;
    @FXML
    public TextField ageTxt;
    @FXML
    public TextField positionTxt;
    @FXML
    public TextField departmentTxt;
    @FXML
    public PasswordField passwordconfirmTxt;

    public void initialize(URL location, ResourceBundle resources) {

    }

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
        // Placeholder for actual authentication logic
        return "manager".equals(username) && "password123".equals(password);
        //TeacherInfo teacherInfo = registrationController.getTeacherInfo(username);
        //return teacherInfo != null && password.equals(teacherInfo.getPassword());
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
    public void hegister(ActionEvent e) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherRegistrationUI.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Teacher Registration");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage.show();
        ((Stage) usernameTxt.getScene().getWindow()).close(); // Close login window
    }
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
    public void close(ActionEvent actionEvent) {
    }
}

