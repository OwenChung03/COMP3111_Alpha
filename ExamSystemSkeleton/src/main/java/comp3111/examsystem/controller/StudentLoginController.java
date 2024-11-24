package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
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

    static Database<Student> studentDatabase;

    private static final String ALLOWED_LOGIN_CHARS = "^[a-zA-Z0-9_]+$";
    private static final String ALLOWED_PASSWORD_CHARS = "^[a-zA-Z0-9!@#\\$%\\^&\\*\\(\\)_\\+\\-=\\[\\]\\{\\}\\|;:'\",<.>/?]+$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        studentDatabase = new Database<>(Student.class);
    }

    // Static field to store the logged-in student
    private static Student loggedInStudent;

    // Getter for the logged-in student
    public static Student getLoggedInStudent() {
        return loggedInStudent;
    }



    //purely UI implementation, I separated the logic into different boolean function.
    //Please don't count it in the coverage ratio
    @FXML
    public void login(ActionEvent e) {
        String username = login_usernameTxt.getText();
        String password = login_passwordTxt.getText();
        List<Student> students = studentDatabase.queryByField("username", username);

        if (emptyStudent(students)) {
            showMsg("Error","Login Failed: No user found.");
            return;
        }
        else if  (isValidLogin(username, password) && passwordCorrect(students, password)) {
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


    /**
     * @param username  studnet unique username
     * @param password  student login password
     * @return
     */
    static boolean isValidLogin(String username, String password) {
        // Replace this with actual login logic (e.g., checking against a database)
        boolean username_flag = username.matches(ALLOWED_LOGIN_CHARS);
        boolean password_flag = password.matches(ALLOWED_PASSWORD_CHARS);

        return username_flag && password_flag;
    }

    static boolean passwordCorrect(List<Student> students, String password){
        return students.get(0).getPassword().equals(password);
    }

    static boolean emptyStudent( List<Student> students){
        return students.isEmpty();
    }


    //purely UI implementation, I separated the logic into different boolean function.
    //Please don't count it in the coverage ratio
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

    public void registerStudent(ActionEvent actionEvent) {

        String username = usernameTxt.getText();
        String name = nameTxt.getText();
        String gender = genderCombo.getValue();
        String ageString = ageTxt.getText();
        String department = departmentTxt.getText();
        String password = passwordTxt.getText();
        String confirmPassword = passwordconfirmTxt.getText();


        if (EmptyRegisterCheck(username,name,gender,ageString,department,password,confirmPassword)) {
            showMsg("Error","Error: All fields are required.");
            return;
        }

        if (!AgeCheck(ageString)){
            showMsg("Error","Please enter a valid age.");
            return;
        }

        if (!ConfirmedPasswordCheck(password,confirmPassword)) {
            showMsg("Error","Error: Passwords do not match.");
            return;
        }

        if(CheckExistingUser(username)) {
            showMsg("Error", "Error: Username already exists. Please choose a different one.");
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

    static boolean EmptyRegisterCheck(String username,String name,String gender,String ageString,String department,String password,String confirmPassword) {
        if (username.isEmpty() || name.isEmpty() || gender == null || ageString.isEmpty()|| department.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            // Show an error message (you can use an Alert dialog for this)
            return true;
        }
        return false;
    }

    static boolean AgeCheck(String ageString){
        try {
            int age = Integer.parseInt(ageString);   // Convert age from String to int
            if (age <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    static boolean ConfirmedPasswordCheck(String password, String confirmPassword){
        if (password.equals(confirmPassword)) {
            return true;
        }
        else{
            return false;
        }
    }

    static boolean CheckExistingUser(String username){
        if (studentDatabase.queryByField("username", username).isEmpty()) {
            return false;
        }
        return true;
    }

}