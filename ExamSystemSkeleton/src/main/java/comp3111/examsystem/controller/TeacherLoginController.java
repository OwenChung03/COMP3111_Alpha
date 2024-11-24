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

    public void initialize(URL location, ResourceBundle resources) {
        TeacherDatabase = new Database<>(Teacher.class);
    }
    // Method to register a new teacher (to be called in your register method)

    static boolean ValidLogin(String username, String password){
        // Initialize the database
        boolean username_flag = username.matches(ALLOWED_LOGIN_CHARS);
        boolean password_flag = password.matches(ALLOWED_PASSWORD_CHARS);
        return username_flag && password_flag;
    }
    static boolean CheckEmpty(List<Teacher> teachers){
        if (teachers.isEmpty()) {
            return true;
        }
        return false;
    }
    static boolean Checkpassword(List<Teacher> teachers, String password){
        if (teachers.get(0).getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        List<Teacher> teachers = TeacherDatabase.queryByField("username", username);
        // Assume we have an validation method
        if (CheckEmpty(teachers)) {
            showMsg("Error", "Login Failed: No user found.");
            return;}
        else if (ValidLogin(username, password) && Checkpassword(teachers,password)) {
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
        }else {
                showMsg("Error", "Invalid username or password.");
            }
    }


    @FXML
    public void register (ActionEvent e){
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
    public void close (ActionEvent e){
        // Get the current stage (window) and close it
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void enterinfo (ActionEvent e){
        // Retrieve data from input fields
        String username = registernameTxt.getText();

        String name = nameTxt.getText();

        String age = ageTxt.getText();

        String gender = genderCombo.getValue();

        String position = PositionCombo.getValue();

        String department = departmentTxt.getText();

        String password = passwordTxt.getText();

        String passwordConfirm = passwordconfirmTxt.getText();

        // Basic validation (you can expand this as needed)
        if (!CheckRegister(username,name,gender,age,position,department,password,passwordConfirm)){
            showMsg("Error", "Error: Please fill in all fields correctly.");
            return;
        }
        else if(!CheckAge(age)) {
            showMsg("Error", "Error: Please enter a valid age.");
            return;
        }
        else if(!checkPassword(password, passwordConfirm)) {

            showMsg("Error", "Error: Passwords do not match.");
            return;
        }
        else if(checkUser(username)) {

            showMsg("Error", "Error: Username already exists. Please choose a different one.");
            return;
        }
        // Create a new Teacher object
        Teacher newTeacher = new Teacher(username, name, gender, age, position, department, password);

        //System.out.println(newTeacher);

        // Add the new student to the database (write to file)
        TeacherDatabase.add(newTeacher);

        showMsg("Welcome", "Teacher registered successfully!");

        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }
    static boolean CheckRegister(String username, String name, String gender, String age, String position, String department, String password, String passwordConfirm) {
        if (username.isEmpty()||name.isEmpty() || gender == null || age.isEmpty() || position == null || department.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()){
            return false;
        }
        return true;
    }
    static boolean CheckAge(String age) {
        try {// Convert age from String to int
            int agenum = Integer.parseInt(age);
            if (agenum <= 0) {
                return false;
            }
        } catch (NumberFormatException e1) {
            return false;
        }
        return true;
    }
    static boolean checkPassword(String password, String passwordConfirm) {
        // Validate that the passwords match
        if (!password.equals(passwordConfirm)) {
            return false;
        }
        return true;
    }
    static boolean checkUser(String username){
        if (TeacherDatabase.queryByField("username", username).isEmpty()) {
             return false;
        }
        return true;
    }
}
