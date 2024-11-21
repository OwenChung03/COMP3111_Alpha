package comp3111.examsystem.controller;
import javafx.collections.ObservableList;
import comp3111.examsystem.tools.Database;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import comp3111.examsystem.entity.Course;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


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
    private CourseLoader courseLoader = new CourseLoader(); // Create an instance of CourseLoader

    @FXML
    public void initialize() {

        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        loadCourses(); // Load courses when the controller initializes
        courseTable.setItems(courseData);
    }

    private void loadCourses() {
        List<Course> courses = courseLoader.loadCoursesFromDatabase(); // Load courses from the database
        courseData.clear(); // Clear existing data
        courseData.addAll(courses); // Add loaded courses to the observable list
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
//package comp3111.examsystem.controller;
//
//import comp3111.examsystem.entity.Course;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//
//public class CourseManageController {
//
//    @FXML
//    private TextField courseIdField, courseNameField, departmentField;
//    @FXML
//    private TextField courseIdFilter, courseNameFilter, departmentFilter;
//
//    @FXML
//    private TableView<Course> courseTable;
//    @FXML
//    private TableColumn<Course, String> courseIdColumn, courseNameColumn, departmentColumn;
//
//    private ObservableList<Course> courseData = FXCollections.observableArrayList();
//
//    @FXML
//    public void initialize() {
//        courseIdColumn.setCellValueFactory(cellData -> cellData.getValue().courseIdProperty());
//        courseNameColumn.setCellValueFactory(cellData -> cellData.getValue().courseNameProperty());
//        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
//
//        courseTable.setItems(courseData);
//    }
//
//    @FXML
//    private void handleAdd() {
//        String courseId = courseIdField.getText();
//        String courseName = courseNameField.getText();
//        String department = departmentField.getText();
//
//        if (courseId.isEmpty() || courseName.isEmpty() || department.isEmpty()) {
//            showAlert("Missing Information", "Please complete all fields before adding a course.");
//            return;
//        }
//
//        Course newCourse = new Course(courseId, courseName, department);
//        courseData.add(newCourse);
//        clearForm();
//    }
//
//    @FXML
//    private void handleUpdate() {
//        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
//        if (selectedCourse != null) {
//            selectedCourse.setCourseId(courseIdField.getText());
//            selectedCourse.setCourseName(courseNameField.getText());
//            selectedCourse.setDepartment(departmentField.getText());
//
//            courseTable.refresh();
//            clearForm();
//        } else {
//            showAlert("No course selected", "Please select a course to update.");
//        }
//    }
//
//    @FXML
//    private void handleDelete() {
//        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
//        if (selectedCourse != null) {
//            courseData.remove(selectedCourse);
//            clearForm();
//        } else {
//            showAlert("No course selected", "Please select a course to delete.");
//        }
//    }
//
//    @FXML
//    private void handleReset() {
//        courseIdFilter.clear();
//        courseNameFilter.clear();
//        departmentFilter.clear();
//        courseTable.setItems(courseData);
//    }
//
//    @FXML
//    private void handleFilter() {
//        String courseId = courseIdFilter.getText().toLowerCase();
//        String courseName = courseNameFilter.getText().toLowerCase();
//        String department = departmentFilter.getText().toLowerCase();
//
//        ObservableList<Course> filteredData = FXCollections.observableArrayList();
//
//        for (Course course : courseData) {
//            if (course.getCourseId().toLowerCase().contains(courseId) &&
//                    course.getCourseName().toLowerCase().contains(courseName) &&
//                    course.getDepartment().toLowerCase().contains(department)) {
//                filteredData.add(course);
//            }
//        }
//
//        courseTable.setItems(filteredData);
//    }
//
//    @FXML
//    private void handleRefresh() {
//        courseTable.refresh();
//    }
//
//    private void clearForm() {
//        courseIdField.clear();
//        courseNameField.clear();
//        departmentField.clear();
//    }
//
//    private void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}