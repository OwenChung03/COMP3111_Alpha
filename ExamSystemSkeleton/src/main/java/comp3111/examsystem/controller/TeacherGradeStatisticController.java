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
import java.util.List;
import java.util.ResourceBundle;

public class TeacherGradeStatisticController implements Initializable {
    public static class GradeExampleClass {
        private String studentName;
        private String courseNum;
        private String examName;
        private String score;
        private String fullScore;
        private String timeSpend;

        // Constructor
        public GradeExampleClass(String studentName, String courseNum, String examName, String score, String fullScore, String timeSpend) {
            this.studentName = studentName;
            this.courseNum = courseNum;
            this.examName = examName;
            this.score = score;
            this.fullScore = fullScore;
            this.timeSpend = timeSpend;
        }

        // Getters
        public String getStudentName() { return studentName; }
        public String getCourseNum() { return courseNum; }
        public String getExamName() { return examName; }
        public String getScore() { return score; }
        public String getFullScore() { return fullScore; }
        public String getTimeSpend() { return timeSpend; }
    }

    @FXML
    private ChoiceBox<String> courseCombox;
    @FXML
    private ChoiceBox<String> examCombox;
    @FXML
    private ChoiceBox<String> studentCombox;
    @FXML
    private TableView<GradeExampleClass> gradeTable;
    @FXML
    private TableColumn<GradeExampleClass, String> studentColumn;
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
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    CategoryAxis categoryAxisLine;
    @FXML
    NumberAxis numberAxisLine;
    @FXML
    PieChart pieChart;

    private final ObservableList<GradeExampleClass> gradeList = FXCollections.observableArrayList();
    private List<String> courses = List.of("COMP3111", "COMP3112", "COMP3113"); // Example courses
    private List<String> exams = List.of("Midterm", "Final", "Quiz 1"); // Example exams
    private List<String> students = List.of("Alice", "Bob", "Charlie"); // Example students

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up the choice boxes
        courseCombox.setItems(FXCollections.observableArrayList(courses));
        examCombox.setItems(FXCollections.observableArrayList(exams));
        studentCombox.setItems(FXCollections.observableArrayList(students));

        // Set up table columns
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
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
        gradeList.add(new GradeExampleClass("Alice", "COMP3111", "Midterm", "85", "100", "50"));
        gradeList.add(new GradeExampleClass("Bob", "COMP3111", "Final", "90", "100", "60"));
        gradeList.add(new GradeExampleClass("Charlie", "COMP3112", "Quiz 1", "75", "80", "30"));

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
        pieChart.getData().clear();
        lineChart.getData().clear();

        // Example Bar Chart Data
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.setName("Average Scores");
        for (String course : courses) {
            seriesBar.getData().add(new XYChart.Data<>(course, Math.random() * 100)); // Replace with actual average scores
        }
        barChart.getData().add(seriesBar);

        // Example Pie Chart Data
        for (String student : students) {
            pieChart.getData().add(new PieChart.Data(student, Math.random() * 100)); // Replace with actual scores
        }

        // Example Line Chart Data
        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.setName("Exam Scores Over Time");
        for (String exam : exams) {
            seriesLine.getData().add(new XYChart.Data<>(exam, Math.random() * 100)); // Replace with actual scores
        }
        lineChart.getData().add(seriesLine);
    }

    @FXML
    public void reset() {
        courseCombox.setValue(null);
        examCombox.setValue(null);
        studentCombox.setValue(null);
        refresh(); // Refresh data to show all
    }

    @FXML
    public void query() {
        // Implement filtering based on selected values
        String selectedCourse = courseCombox.getValue();
        String selectedExam = examCombox.getValue();
        String selectedStudent = studentCombox.getValue();

        ObservableList<GradeExampleClass> filteredList = FXCollections.observableArrayList();
        for (GradeExampleClass grade : gradeList) {
            if ((selectedCourse == null || grade.getCourseNum().equals(selectedCourse)) &&
                    (selectedExam == null || grade.getExamName().equals(selectedExam)) &&
                    (selectedStudent == null || grade.getStudentName().equals(selectedStudent))) {
                filteredList.add(grade);
            }
        }
        gradeTable.setItems(filteredList);
        loadChart(); // Update charts based on filters
    }
}
