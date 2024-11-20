package comp3111.examsystem.controller;

import comp3111.examsystem.entity.StudentGradeData;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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

    // Example course data for demonstration
    private final List<String> courses = List.of("COMP3111", "COMP3112", "COMP3113");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize choice box with courses
        courseCombox.setItems(FXCollections.observableArrayList(courses));

        // Set up table columns
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalScore"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpentColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));

        // Load initial data into the table and chart
        loadGradeData();
        loadChart();
    }

    // Load student grade data (this would typically come from a database)
    private void loadGradeData() {
        gradeList.clear();  // Clear current data

        // Example data (in a real application, this would come from a database or API)
        gradeList.add(new StudentGradeData("1", "COMP3111", "Midterm", "85", "100", "30 minutes 50 seconds"));
        gradeList.add(new StudentGradeData("1", "COMP3111", "Final", "90", "100", "40 minutes 20 seconds"));
        gradeList.add(new StudentGradeData("1", "COMP3112", "Quiz 1", "75", "80", "25 minutes 30 seconds"));
        gradeList.add(new StudentGradeData("2", "COMP3113", "Quiz 1", "70", "80", "20 minutes 10 seconds"));

        gradeTable.setItems(gradeList);  // Populate the table with data
    }

    // Refresh the table and chart when the user performs an action
    @FXML
    public void refresh() {
        loadGradeData();  // Reload data
        loadChart();      // Reload chart data
    }

    // Load bar chart data based on the average scores per course
    private void loadChart() {
        barChart.getData().clear();  // Clear existing chart data

        // Calculate average scores per course
        Map<String, Double> averageScores = calculateAverageScoresPerCourse();

        // Create new chart series
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.setName("Average Scores");

        // Populate the series with the average scores
        for (Map.Entry<String, Double> entry : averageScores.entrySet()) {
            seriesBar.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Add the series to the bar chart
        barChart.getData().add(seriesBar);
    }

    // Calculate the average score for each course
    private Map<String, Double> calculateAverageScoresPerCourse() {
        Map<String, Double> averageScores = new HashMap<>();
        Map<String, Integer> courseTotalScores = new HashMap<>();
        Map<String, Integer> courseFullScores = new HashMap<>();

        // Sum the total and full scores for each course
        for (StudentGradeData grade : gradeList) {
            String courseId = grade.getCourseId();
            int totalScore = Integer.parseInt(grade.getTotalScore());
            int fullScore = Integer.parseInt(grade.getFullScore());

            courseTotalScores.put(courseId, courseTotalScores.getOrDefault(courseId, 0) + totalScore);
            courseFullScores.put(courseId, courseFullScores.getOrDefault(courseId, 0) + fullScore);
        }

        // Calculate average score as a percentage for each course
        for (String courseId : courseTotalScores.keySet()) {
            double average = (double) courseTotalScores.get(courseId) / courseFullScores.get(courseId) * 100;
            averageScores.put(courseId, average);
        }

        return averageScores;
    }

    // Reset filters and reload data
    @FXML
    public void reset() {
        courseCombox.setValue(null);  // Clear the course filter
        refresh();  // Reload data without filters
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
        loadChart();
    }
}