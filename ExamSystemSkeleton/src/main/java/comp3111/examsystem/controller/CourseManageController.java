package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Course;
import comp3111.examsystem.tools.Database; // Ensure you have this import
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CourseManageController {

    @FXML
    private TextField courseIdField, courseNameField, departmentField;
    @FXML
    private TextField courseIdFilter, courseNameFilter, departmentFilter;

    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseIdColumn, courseNameColumn, departmentColumn;

    private ObservableList<Course> courseData = FXCollections.observableArrayList();
    private Database<Course> courseDatabase; // Database instance

    @FXML
    public void initialize() {
        courseDatabase = new Database<>(Course.class); // Initialize the database
        setupColumns(); // Set up the columns for the table
        loadCoursesFromDatabase(); // Load the initial data
    }

    private void setupColumns() {
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        courseTable.setItems(courseData);
    }

    private void loadCoursesFromDatabase() {
        courseData.setAll(courseDatabase.getAll()); // Load all courses from the database
        courseTable.setItems(courseData); // Set the loaded data to the table
    }

    @FXML
    private void handleAdd() {
        String courseId = courseIdField.getText();
        String courseName = courseNameField.getText();
        String department = departmentField.getText();

        if (courseId.isEmpty() || courseName.isEmpty() || department.isEmpty()) {
            showAlert("Missing Information", "Please complete all fields before adding a course.");
            return;
        }

        Course newCourse = new Course(courseId, courseName, department);
        courseData.add(newCourse);
        courseDatabase.add(newCourse); // Add the new course to the database
        clearForm();
    }


    @FXML
    private void handleUpdate() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            boolean modified = false; // Track if any modifications were made

            // Update courseId if changed
            String newCourseId = courseIdField.getText().trim();
            if (!newCourseId.isEmpty() && !newCourseId.equals(selectedCourse.getCourseId())) {
                selectedCourse.setCourseId(newCourseId);
                modified = true;
            }

            // Update courseName if changed
            String newCourseName = courseNameField.getText().trim();
            if (!newCourseName.isEmpty() && !newCourseName.equals(selectedCourse.getCourseName())) {
                selectedCourse.setCourseName(newCourseName);
                modified = true;
            }

            // Update department if changed
            String newDepartment = departmentField.getText().trim();
            if (!newDepartment.isEmpty() && !newDepartment.equals(selectedCourse.getDepartment())) {
                selectedCourse.setDepartment(newDepartment);
                modified = true;
            }

            // Only update the database if any modifications were made
            if (modified) {
                courseDatabase.update(selectedCourse); // Update the course in the database
                courseTable.refresh();
                clearForm();
            } else {
                showAlert("No changes made", "Please modify at least one field to update the course.");
            }
        } else {
            showAlert("No course selected", "Please select a course to update.");
        }
    }
    @FXML
    private void handleDelete() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String courseId = selectedCourse.getCourseId(); // Use courseId for deletion
            courseData.remove(selectedCourse);
            courseDatabase.delByFiled("courseId", courseId); // Delete from database by courseId
            clearForm();
        } else {
            showAlert("No course selected", "Please select a course to delete.");
        }
    }

    @FXML
    private void handleReset() {
        courseIdFilter.clear();
        courseNameFilter.clear();
        departmentFilter.clear();
        courseTable.setItems(courseData);
    }

    @FXML
    private void handleFilter() {
        String courseId = courseIdFilter.getText().toLowerCase();
        String courseName = courseNameFilter.getText().toLowerCase();
        String department = departmentFilter.getText().toLowerCase();

        ObservableList<Course> filteredData = FXCollections.observableArrayList();

        for (Course course : courseData) {
            if (course.getCourseId().toLowerCase().contains(courseId) &&
                    course.getCourseName().toLowerCase().contains(courseName) &&
                    course.getDepartment().toLowerCase().contains(department)) {
                filteredData.add(course);
            }
        }

        courseTable.setItems(filteredData);
    }

    @FXML
    private void handleRefresh() {
        loadCoursesFromDatabase(); // Reload courses from the database
    }

    private void clearForm() {
        courseIdField.clear();
        courseNameField.clear();
        departmentField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}