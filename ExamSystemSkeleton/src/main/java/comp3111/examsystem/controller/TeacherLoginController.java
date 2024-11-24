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
import comp3111.examsystem.entity.Teacher;

/**
 * Controller class for handling teacher login and registration functionalities in the examination system.
 * This class manages user interactions for logging in and registering teachers, including validation of
 * input data and communication with the teacher database.
 */
public class TeacherLoginController implements Initializable {

    private static final String ALLOWED_LOGIN_CHARS = "^[a-zA-Z0-9_]+$";
    private static final String ALLOWED_PASSWORD_CHARS = "^[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\}\\|;:'\",<.>/?]+$";

    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField registernameTxt;
    @FXML
    private PasswordField passwordTxt;
    private Button loginButton;
    private Button registerButton;
    public TextField nameTxt;
    public ComboBox<String> genderCombo; // Ensure this matches the fx:id in FXML
    public TextField ageTxt;
    public ComboBox<String> PositionCombo;
    public TextField departmentTxt;
    public PasswordField passwordconfirmTxt;
    static Database<Teacher> TeacherDatabase;

    /**
     * Initializes the controller and the teacher database.
     * This method is automatically called after the FXML file has been loaded.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        TeacherDatabase = new Database<>(Teacher.class);
    }

    /**
     * Validates the login credentials against the allowed character patterns.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return true if both the username and password match the allowed patterns; false otherwise.
     */
    static boolean ValidLogin(String username, String password) {
        boolean username_flag = username.matches(ALLOWED_LOGIN_CHARS);
        boolean password_flag = password.matches(ALLOWED_PASSWORD_CHARS);
        return username_flag && password_flag;
    }

    /**
     * Checks if the provided list of teachers is empty.
     *
     * @param teachers The list of teachers to check.
     * @return true if the list is empty; false otherwise.
     */
    static boolean CheckEmpty(List<Teacher> teachers) {
        return teachers.isEmpty();
    }

    /**
     * Checks if the provided password matches the password of the first teacher in the list.
     *
     * @param teachers The list of teachers.
     * @param password The password to check against.
     * @return true if the password matches; false otherwise.
     */
    static boolean Checkpassword(List<Teacher> teachers, String password) {
        return teachers.get(0).getPassword().equals(password);
    }

    /**
     * Handles the login action for the teacher.
     * Validates the username and password, and if successful, opens the main UI.
     *
     * @param e The ActionEvent triggered by the login button.
     */
    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        List<Teacher> teachers = TeacherDatabase.queryByField("username", username);

        if (CheckEmpty(teachers)) {
            showMsg("Error", "Login Failed: No user found.");
            return;
        } else if (ValidLogin(username, password) && Checkpassword(teachers, password)) {
            showMsg("Welcome", "Login Successful");

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
        } else {
            showMsg("Error", "Invalid username or password.");
        }
    }

    /**
     * Opens the teacher registration UI when the register button is clicked.
     *
     * @param e The ActionEvent triggered by the register button.
     */
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

    /**
     * Closes the current window when the close button is clicked.
     *
     * @param e The ActionEvent triggered by the close button.
     */
    @FXML
    public void close(ActionEvent e) {
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the information submission during the registration process.
     * Validates the input data and registers a new teacher if valid.
     *
     * @param e The ActionEvent triggered by the submit button.
     */
    @FXML
    public void enterinfo(ActionEvent e) {
        String username = registernameTxt.getText();
        String name = nameTxt.getText();
        String age = ageTxt.getText();
        String gender = genderCombo.getValue();
        String position = PositionCombo.getValue();
        String department = departmentTxt.getText();
        String password = passwordTxt.getText();
        String passwordConfirm = passwordconfirmTxt.getText();

        if (!CheckRegister(username, name, gender, age, position, department, password, passwordConfirm)) {
            showMsg("Error", "Error: Please fill in all fields correctly.");
            return;
        } else if (!CheckAge(age)) {
            showMsg("Error", "Error: Please enter a valid age.");
            return;
        } else if (!checkPassword(password, passwordConfirm)) {
            showMsg("Error", "Error: Passwords do not match.");
            return;
        } else if (checkUser(username)) {
            showMsg("Error", "Error: Username already exists. Please choose a different one.");
            return;
        }

        Teacher newTeacher = new Teacher(username, name, gender, age, position, department, password);
        TeacherDatabase.add(newTeacher);
        showMsg("Welcome", "Teacher registered successfully!");

        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Checks if all required fields for registration are filled correctly.
     *
     * @param username    The username input.
     * @param name        The name input.
     * @param gender      The gender selection.
     * @param age         The age input.
     * @param position     The position selection.
     * @param department   The department input.
     * @param password    The password input.
     * @param passwordConfirm The password confirmation input.
     * @return true if all fields are filled correctly; false otherwise.
     */
    static boolean CheckRegister(String username, String name, String gender, String age, String position, String department, String password, String passwordConfirm) {
        return !(username.isEmpty() || name.isEmpty() || gender == null || age.isEmpty() || position == null || department.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty());
    }

    /**
     * Validates the age input to ensure it is a positive integer.
     *
     * @param age The age input as a string.
     * @return true if the age is valid; false otherwise.
     */
    static boolean CheckAge(String age) {
        try {
            int agenum = Integer.parseInt(age);
            return agenum > 0;
        } catch (NumberFormatException e1) {
            return false;
        }
    }

    /**
     * Checks if the provided passwords match.
     *
     * @param password The password input.
     * @param passwordConfirm The password confirmation input.
     * @return true if the passwords match; false otherwise.
     */
    static boolean checkPassword(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    /**
     * Checks if the given username already exists in the database.
     *
     * @param username The username to check.
     * @return true if the username exists; false otherwise.
     */
    static boolean checkUser(String username) {
        return !TeacherDatabase.queryByField("username", username).isEmpty();
    }
}