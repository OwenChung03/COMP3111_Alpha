package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Course;
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

    @FXML
    public void initialize() {
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId")); // Adjust as necessary
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        courseTable.setItems(courseData);
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
        clearForm();
    }

    @FXML
    private void handleUpdate() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            selectedCourse.setCourseId(courseIdField.getText());
            selectedCourse.setCourseName(courseNameField.getText());
            selectedCourse.setDepartment(departmentField.getText());

            courseTable.refresh();
            clearForm();
        } else {
            showAlert("No course selected", "Please select a course to update.");
        }
    }

    @FXML
    private void handleDelete() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courseData.remove(selectedCourse);
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
        courseTable.refresh();
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