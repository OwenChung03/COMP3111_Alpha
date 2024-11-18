package comp3111.examsystem.controller;

import comp3111.examsystem.Entity.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import comp3111.examsystem.tools.Database;
public class CourseManageController {

    @FXML
    private TextField courseIdFilter, courseNameFilter, departmentFilter;
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TextField newCourseId, newCourseName, newDepartment;

    private ObservableList<Course> courseList = FXCollections.observableArrayList();
    private Database<Course> courseDatabase;

    @FXML
    public void initialize() {
        courseDatabase = new Database<>(Course.class);

        // Set up the table columns
        TableColumn<Course, String> courseIdColumn = new TableColumn<>("Course ID");
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));

        TableColumn<Course, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        TableColumn<Course, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        courseTable.getColumns().addAll(courseIdColumn, courseNameColumn, departmentColumn);
        loadCoursesFromDatabase();
    }

    private void loadCoursesFromDatabase() {
        courseList.setAll(courseDatabase.getAll());
        courseTable.setItems(courseList);
    }

    @FXML
    public void filterCourses() {
        String courseId = courseIdFilter.getText().toLowerCase();
        String courseName = courseNameFilter.getText().toLowerCase();
        String department = departmentFilter.getText().toLowerCase();

        ObservableList<Course> filteredList = FXCollections.observableArrayList();

        for (Course course : courseList) {
            boolean matches = true;
            if (!courseId.isEmpty() && !course.getCourseId().toLowerCase().contains(courseId)) {
                matches = false;
            }
            if (!courseName.isEmpty() && !course.getCourseName().toLowerCase().contains(courseName)) {
                matches = false;
            }
            if (!department.isEmpty() && !course.getDepartment().toLowerCase().contains(department)) {
                matches = false;
            }
            if (matches) {
                filteredList.add(course);
            }
        }

        courseTable.setItems(filteredList);
    }

    @FXML
    public void resetFilters() {
        courseIdFilter.clear();
        courseNameFilter.clear();
        departmentFilter.clear();
        courseTable.setItems(courseList);
    }

    @FXML
    public void addCourse() {
        String courseId = newCourseId.getText();
        String courseName = newCourseName.getText();
        String department = newDepartment.getText();

        if (courseId.isEmpty() || courseName.isEmpty() || department.isEmpty()) {
            showMsg("Error: All fields are required.");
            return;
        }

        Course newCourse = new Course(courseId, courseName, department);
        courseDatabase.add(newCourse);
        loadCoursesFromDatabase();
    }

    @FXML
    public void updateCourse() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            selectedCourse.setCourseId(newCourseId.getText());
            selectedCourse.setCourseName(newCourseName.getText());
            selectedCourse.setDepartment(newDepartment.getText());
            courseDatabase.update(selectedCourse);
            loadCoursesFromDatabase();
        }
    }

    @FXML
    public void deleteCourse() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courseDatabase.delByFiled("courseId", selectedCourse.getCourseId());
            loadCoursesFromDatabase();
        }
    }

    @FXML
    public void refreshCourses() {
        loadCoursesFromDatabase();
    }

    private void showMsg(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}