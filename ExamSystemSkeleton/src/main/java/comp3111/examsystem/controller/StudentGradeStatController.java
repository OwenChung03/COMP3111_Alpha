package comp3111.examsystem.controller;

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

public class StudentGradeStatController implements Initializable {
    public static class GradeExampleClass {

        private String courseNum;
        private String examName;
        private String score;
        private String fullScore;
        private String timeSpend;

        // Constructor
        public GradeExampleClass(String courseNum, String examName, String score, String fullScore, String timeSpend) {
            this.courseNum = courseNum;
            this.examName = examName;
            this.score = score;
            this.fullScore = fullScore;
            this.timeSpend = timeSpend;
        }

        // Getters

        public String getCourseNum() { return courseNum; }
        public String getExamName() { return examName; }
        public String getScore() { return score; }
        public String getFullScore() { return fullScore; }
        public String getTimeSpend() { return timeSpend; }
    }

    @FXML
    private ChoiceBox<String> courseCombox;
    @FXML
    private TableView<GradeExampleClass> gradeTable;
    @FXML
    private TableColumn<GradeExampleClass, String> courseColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> examColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> scoreColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> fullScoreColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> timeSpendColumn;
    @FXML
    BarChart<String, Number> barChart;
    @FXML
    CategoryAxis categoryAxisBar;
    @FXML
    NumberAxis numberAxisBar;


    private final ObservableList<GradeExampleClass> gradeList = FXCollections.observableArrayList();
    private List<String> courses = List.of("COMP3111", "COMP3112", "COMP3113"); // Example courses
    private List<String> exams = List.of("Midterm", "Final", "Quiz 1"); // Example exams
    private List<String> students = List.of("Alice", "Bob", "Charlie"); // Example students

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up the choice boxes
        courseCombox.setItems(FXCollections.observableArrayList(courses));


        // Set up table columns
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseNum"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpend"));

        // Load initial data
        loadGradeData();
        loadChart();
    }

    private void loadGradeData() {
        // Example data - in a real application, this would come from a database or an API
        gradeList.clear();
        gradeList.add(new GradeExampleClass( "COMP3111", "Midterm", "85", "100", "50"));
        gradeList.add(new GradeExampleClass( "COMP3111", "Final", "90", "100", "60"));
        gradeList.add(new GradeExampleClass( "COMP3112", "Quiz 1", "75", "80", "30"));

        gradeTable.setItems(gradeList);
    }

    @FXML
    public void refresh() {
        loadGradeData(); // Reload data on refresh
        loadChart(); // Reload charts on refresh
    }

    private void loadChart() {
        // Clear existing data
        barChart.getData().clear();

        // Example Bar Chart Data
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.setName("Average Scores");
        for (String course : courses) {
            seriesBar.getData().add(new XYChart.Data<>(course, Math.random() * 100)); // Replace with actual average scores
        }
        barChart.getData().add(seriesBar);

    }

    @FXML
    public void reset() {
        courseCombox.setValue(null);
        refresh(); // Refresh data to show all
    }

    @FXML
    public void query() {
        // Implement filtering based on selected values
        String selectedCourse = courseCombox.getValue();


        ObservableList<GradeExampleClass> filteredList = FXCollections.observableArrayList();
        for (GradeExampleClass grade : gradeList) {
            if ((selectedCourse == null || grade.getCourseNum().equals(selectedCourse))){
                filteredList.add(grade);
            }
        }
        gradeTable.setItems(filteredList);
        loadChart(); // Update charts based on filters
    }
}