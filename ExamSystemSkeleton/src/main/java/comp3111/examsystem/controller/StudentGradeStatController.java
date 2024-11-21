package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Course;
import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.entity.StudentGradeData;
import comp3111.examsystem.tools.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class StudentGradeStatController implements Initializable {

    @FXML
    private ChoiceBox<String> courseCombox;

    @FXML
    private TableView<StudentGradeData> gradeTable;

    @FXML
    private TableColumn<StudentGradeData, String> courseColumn;

    @FXML
    private TableColumn<StudentGradeData, String> examColumn;

    @FXML
    private TableColumn<StudentGradeData, Integer> scoreColumn;

    @FXML
    private TableColumn<StudentGradeData, Integer> fullScoreColumn;

    @FXML
    private TableColumn<StudentGradeData, String> timeSpentColumn;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis categoryAxisBar;

    @FXML
    private NumberAxis numberAxisBar;

    // Observable list to hold student grade data
    private final ObservableList<StudentGradeData> gradeList = FXCollections.observableArrayList();

    // Database instance to load StudentGradeData
    private Database<StudentGradeData> studentGradeDatabase;
    private Database<Course> courseDatabase;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize database for StudentGradeData
        studentGradeDatabase = new Database<>(StudentGradeData.class);
        courseDatabase = new Database<>(Course.class);

        loadCourses();
        setupTableColumns();
        loadGradeData();
        loadChart(gradeList);
    }

    private void setupTableColumns() {
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalScore"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpentColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));
    }

    private void loadCourses() {
        List<Course> coursesFromFile = courseDatabase.getAll();
        List<String> courseNames = coursesFromFile.stream()
                .map(Course::getCourseId)  // Extract courseId from each Course entity
                .collect(Collectors.toList());

        // Populate the course choice box with course names
        courseCombox.setItems(FXCollections.observableArrayList(courseNames));
    }

    // Load student grade data (this would typically come from a database)
    private void loadGradeData() {
        gradeList.clear();  // Clear current data

        Student loggedInStudent = StudentLoginController.getLoggedInStudent();

        String studentIdStr = String.valueOf(loggedInStudent.getId());

        // Retrieve all student grade data from the database
        List<StudentGradeData> studentGradeData = studentGradeDatabase.queryByField("studentId", studentIdStr);

        // Add the filtered data to the gradeList
        gradeList.addAll(studentGradeData);

        // Set the items in the TableView
        gradeTable.setItems(gradeList);
    }

    // Refresh the table and chart when the user performs an action
    @FXML
    public void refresh() {
        loadGradeData();  // Reload data
        loadChart(gradeList);      // Reload chart data
    }

    // Load bar chart data based on the filtered grade data
    private void loadChart(ObservableList<StudentGradeData> data) {
        // Clear the BarChart data and categories
        barChart.getData().clear();
        categoryAxisBar.getCategories().clear();

        // Create a new series for the chart
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.setName("Exam Scores");

        // Use a Set to track unique categories (to avoid duplicates)
        Set<String> uniqueCategories = new LinkedHashSet<>();

        // Populate the series with the actual scores for each exam
        for (StudentGradeData grade : data) {
            try {
                // Convert totalScore from String to Integer
                int totalScore = Integer.parseInt(grade.getTotalScore());

                // Create a unique label by combining course name and exam name
                String examLabel = grade.getCourseId() + " - " + grade.getExamName();

                // Add the category to the Set
                uniqueCategories.add(examLabel);

                // Add data to the series with the combined label
                seriesBar.getData().add(new XYChart.Data<>(examLabel, totalScore));

            } catch (NumberFormatException e) {
                // Handle any potential parsing errors
                System.err.println("Failed to parse totalScore for exam: " + grade.getExamName());
            }
        }

        // Add the unique categories to the CategoryAxis
        categoryAxisBar.getCategories().addAll(uniqueCategories);

        // Add the series to the bar chart
        barChart.getData().add(seriesBar);

        // Adjust X-axis labels to avoid overlap (rotate by 45 degrees)
        categoryAxisBar.setTickLabelRotation(45);

        // Force the BarChart to layout again with the updated data
        barChart.applyCss(); // Forces CSS to reapply
        barChart.layout();   // Forces JavaFX to recalculate layout
    }

    // Reset filters and reload data
    @FXML
    public void reset() {
        courseCombox.setValue(null);  // Clear the course filter
        refresh();  // First call updates the data
    }

    // Filter the table and chart based on the selected course
    @FXML
    public void query() {
        String selectedCourse = courseCombox.getValue();  // Get selected course

        // Filter the grade list based on the selected course
        ObservableList<StudentGradeData> filteredList = gradeList.stream()
                .filter(grade -> selectedCourse == null || grade.getCourseId().equals(selectedCourse))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        // Update the table with the filtered data
        gradeTable.setItems(filteredList);

        // Reload the chart to reflect the filtered data
        loadChart(filteredList);  // First call
    }
}