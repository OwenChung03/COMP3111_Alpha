package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import comp3111.examsystem.tools.Database;

public class TeacherManageController {

    @FXML
    private TextField usernameField, nameField, ageField, departmentField, passwordField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField usernameFilter, nameFilter, departmentFilter;

    @FXML
    private TableView<Teacher> teacherTable;
    @FXML
    private TableColumn<Teacher, String> usernameColumn, nameColumn, ageColumn, genderColumn, departmentColumn, passwordColumn;

    private ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
    private Database<Teacher> teacherDatabase;

    @FXML
    public void initialize() {
        teacherDatabase = new Database<>(Teacher.class);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        loadTeachersFromDatabase();
    }

    private void loadTeachersFromDatabase() {
        teacherList.setAll(teacherDatabase.getAll());
        teacherTable.setItems(teacherList);
    }

    @FXML
    public void filterTeachers() {
        String username = usernameFilter.getText().toLowerCase();
        String name = nameFilter.getText().toLowerCase();
        String department = departmentFilter.getText().toLowerCase();

        ObservableList<Teacher> filteredList = FXCollections.observableArrayList();

        for (Teacher teacher : teacherList) {
            boolean matches = true;
            if (!username.isEmpty() && !teacher.getUsername().toLowerCase().contains(username)) {
                matches = false;
            }
            if (!name.isEmpty() && !teacher.getName().toLowerCase().contains(name)) {
                matches = false;
            }
            if (!department.isEmpty() && !teacher.getDepartment().toLowerCase().contains(department)) {
                matches = false;
            }
            if (matches) {
                filteredList.add(teacher);
            }
        }

        teacherTable.setItems(filteredList);
    }

    @FXML
    public void resetFilters() {
        usernameFilter.clear();
        nameFilter.clear();
        departmentFilter.clear();
        teacherTable.setItems(teacherList);
    }

    @FXML
    public void addTeacher() {
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

        Teacher newTeacher = new Teacher(username, name, gender, age, "Teacher", department, password);
        teacherDatabase.add(newTeacher);
        loadTeachersFromDatabase();
    }

    @FXML
    public void updateTeacher() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            boolean isModified = false;

            // Check for modifications in each field
            String newUsername = usernameField.getText().trim();
            String newName = nameField.getText().trim();
            String newAge = ageField.getText().trim();
            String newGender = genderComboBox.getValue();
            String newDepartment = departmentField.getText().trim();
            String newPassword = passwordField.getText().trim();

            // Update fields only if they are changed
            if (!newUsername.isEmpty() && !newUsername.equals(selectedTeacher.getUsername())) {
                selectedTeacher.setUsername(newUsername);
                isModified = true;
            }

            if (!newName.isEmpty() && !newName.equals(selectedTeacher.getName())) {
                selectedTeacher.setName(newName);
                isModified = true;
            }

            if (!newAge.isEmpty() && !newAge.equals(selectedTeacher.getAge())) {
                selectedTeacher.setAge(newAge);
                isModified = true;
            }

            if (newGender != null && !newGender.equals(selectedTeacher.getGender())) {
                selectedTeacher.setGender(newGender);
                isModified = true;
            }

            if (!newDepartment.isEmpty() && !newDepartment.equals(selectedTeacher.getDepartment())) {
                selectedTeacher.setDepartment(newDepartment);
                isModified = true;
            }

            // Only allow password update if at least one other field is modified
            if (!newPassword.isEmpty()) {
                if (isModified) {
                    selectedTeacher.setPassword(newPassword);
                } else {
                    showMsg("Error: You must change at least one other field to update the password.");
                    return;
                }
            }

            // If any modification occurs, update the database
            if (isModified || !newPassword.isEmpty()) {
                teacherDatabase.update(selectedTeacher); // Update the teacher in the database
                loadTeachersFromDatabase(); // Refresh the table
            } else {
                showMsg("Error: No changes made.");
            }
        } else {
            showMsg("Error: No teacher selected.");
        }
    }

    @FXML
    public void deleteTeacher() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            teacherDatabase.delByFiled("username", selectedTeacher.getUsername());
            loadTeachersFromDatabase();
        }
    }

    @FXML
    public void refreshTeachers() {
        loadTeachersFromDatabase();
    }

    private void showMsg(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}