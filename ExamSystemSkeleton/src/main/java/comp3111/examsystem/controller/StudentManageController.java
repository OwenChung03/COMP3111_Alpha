package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
import comp3111.examsystem.tools.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentManageController {

    @FXML
    private TextField usernameFilter, nameFilter, departmentFilter;
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> usernameColumn, nameColumn, ageColumn, genderColumn, departmentColumn, passwordColumn;
    @FXML
    private TextField usernameField, nameField, ageField, departmentField, passwordField;
    @FXML
    private ComboBox<String> genderComboBox;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private Database<Student> studentDatabase; // Database instance

    @FXML
    public void initialize() {
        studentDatabase = new Database<>(Student.class); // Initialize the database

        // Set up the cell value factories for each column
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        // Initialize ComboBox items
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Load initial data
        loadStudentsFromDatabase();
    }

    private void loadStudentsFromDatabase() {
        studentList.setAll(studentDatabase.getAll());
        studentTable.setItems(studentList);
    }

    @FXML
    public void filterStudents() {
        String username = usernameFilter.getText().toLowerCase();
        String name = nameFilter.getText().toLowerCase();
        String department = departmentFilter.getText().toLowerCase();

        ObservableList<Student> filteredList = FXCollections.observableArrayList();

        for (Student student : studentList) {
            boolean matches = true;
            if (!username.isEmpty() && !student.getUsername().toLowerCase().contains(username)) {
                matches = false;
            }
            if (!name.isEmpty() && !student.getName().toLowerCase().contains(name)) {
                matches = false;
            }
            if (!department.isEmpty() && !student.getDepartment().toLowerCase().contains(department)) {
                matches = false;
            }
            if (matches) {
                filteredList.add(student);
            }
        }

        studentTable.setItems(filteredList);
    }

    @FXML
    public void resetFilters() {
        usernameFilter.clear();
        nameFilter.clear();
        departmentFilter.clear();
        studentTable.setItems(studentList);
    }

    @FXML
    public void addStudent() {
        String username = usernameField.getText();
        String name = nameField.getText();
        String gender = genderComboBox.getValue();
        String age = ageField.getText();
        String department = departmentField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || name.isEmpty() || gender == null || age.isEmpty() || department.isEmpty() || password.isEmpty()) {
            showMsg("Error: All fields are required.");
            return;
        }

        // Create a new Student object
        Student newStudent = new Student(null, username, name, gender, age, department, password);

        // Add the new student to the database
        studentDatabase.add(newStudent);
        loadStudentsFromDatabase();
    }

    @FXML
    public void updateStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            boolean isModified = false;

            // Check for modifications in each field
            String newUsername = usernameField.getText().trim();
            String newName = nameField.getText().trim();
            String newAge = ageField.getText().trim();
            String newGender = genderComboBox.getValue();
            String newDepartment = departmentField.getText().trim();
            String newPassword = passwordField.getText().trim();

            // Update fields only if they are changed
            if (!newUsername.isEmpty() && !newUsername.equals(selectedStudent.getUsername())) {
                selectedStudent.setUsername(newUsername);
                isModified = true;
            }

            if (!newName.isEmpty() && !newName.equals(selectedStudent.getName())) {
                selectedStudent.setName(newName);
                isModified = true;
            }

            if (!newAge.isEmpty() && !newAge.equals(selectedStudent.getAge())) {
                selectedStudent.setAge(newAge);
                isModified = true;
            }

            if (newGender != null && !newGender.equals(selectedStudent.getGender())) {
                selectedStudent.setGender(newGender);
                isModified = true;
            }

            if (!newDepartment.isEmpty() && !newDepartment.equals(selectedStudent.getDepartment())) {
                selectedStudent.setDepartment(newDepartment);
                isModified = true;
            }

            // Only allow password update if at least one other field is modified
            if (!newPassword.isEmpty()) {
                if (isModified) {
                    selectedStudent.setPassword(newPassword);
                } else {
                    showMsg("Error: You must change at least one other field to update the password.");
                    return;
                }
            }

            // If any modification occurs, update the database
            if (isModified || !newPassword.isEmpty()) {
                studentDatabase.update(selectedStudent); // Update the student in the database
                loadStudentsFromDatabase(); // Refresh the table
            } else {
                showMsg("Error: No changes made.");
            }
        } else {
            showMsg("Error: No student selected.");
        }
    }

    @FXML
    public void deleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            String username = selectedStudent.getUsername();
            studentDatabase.delByFiled("username", username);
            loadStudentsFromDatabase();
        } else {
            showMsg("Error: No student selected.");
        }
    }

    @FXML
    public void refreshStudents() {
        loadStudentsFromDatabase();
    }

    private void showMsg(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}