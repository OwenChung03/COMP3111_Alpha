package comp3111.examsystem.controller;

import comp3111.examsystem.Entity.Student;
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
    private TextField usernameField, nameField, ageField, departmentField, passwordField;
    @FXML
    private ComboBox<String> genderComboBox;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private Database<Student> studentDatabase; // Database instance

    @FXML
    public void initialize() {
        studentDatabase = new Database<>(Student.class); // Initialize database

        // Set up the table columns
        TableColumn<Student, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Student, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Student, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<Student, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        studentTable.getColumns().addAll(usernameColumn, nameColumn, ageColumn, genderColumn, departmentColumn, passwordColumn);

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
            selectedStudent.setUsername(usernameField.getText());
            selectedStudent.setName(nameField.getText());
            selectedStudent.setAge(ageField.getText());
            selectedStudent.setGender(genderComboBox.getValue());
            selectedStudent.setDepartment(departmentField.getText());
            selectedStudent.setPassword(passwordField.getText());

            // Update the student in the database
            studentDatabase.update(selectedStudent);
            loadStudentsFromDatabase();
        }
    }

    @FXML
    public void deleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            String username = selectedStudent.getUsername();
            studentDatabase.delByFiled("username", username);
            loadStudentsFromDatabase();
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