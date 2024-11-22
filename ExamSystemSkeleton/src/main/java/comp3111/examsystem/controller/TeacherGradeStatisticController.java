package comp3111.examsystem.controller;

import javafx.beans.property.SimpleStringProperty;
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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TeacherGradeStatisticController implements Initializable {

    @FXML
    private ChoiceBox<String> courseCombox;
    @FXML
    private ChoiceBox<String> examCombox;
    @FXML
    private ChoiceBox<String> studentCombox;

    @FXML
    private TableView<StudentGradeData> gradeTable;
    @FXML
    private TableColumn<StudentGradeData, String> studentColumn;
    @FXML
    private TableColumn<StudentGradeData, String> courseColumn;
    @FXML
    private TableColumn<StudentGradeData, String> examColumn;
    @FXML
    private TableColumn<StudentGradeData, String> scoreColumn;
    @FXML
    private TableColumn<StudentGradeData, String> fullScoreColumn;
    @FXML
    private TableColumn<StudentGradeData, String> timeSpendColumn;

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis categoryAxisBar;
    @FXML
    private NumberAxis numberAxisBar;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private CategoryAxis categoryAxisLine;
    @FXML
    private NumberAxis numberAxisLine;
    @FXML
    private PieChart pieChart;

    private final ObservableList<StudentGradeData> gradeList = FXCollections.observableArrayList();

    private Database<Course> courseDB;
    private Database<Exam> examDB;
    private Database<Student> studentDB;
    private Database<StudentGradeData> gradeDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize databases
        courseDB = new Database<>(Course.class);
        examDB = new Database<>(Exam.class);
        studentDB = new Database<>(Student.class);
        gradeDB = new Database<>(StudentGradeData.class);

        // Set up choice boxes
        setupComboBoxes();

        // Setup table columns
        setupTableColumns();

        // Setup Charts
        setupCharts();

        // Load initial data
        loadGradeData();
        loadChart();
    }

    private void setupComboBoxes() {
        List<String> courses = courseDB.getAll().stream()
                .map(Course::getCourseId)
                .collect(Collectors.toList());
        courseCombox.setItems(FXCollections.observableArrayList(courses));

        List<String> exams = examDB.getAll().stream()
                .map(Exam::getExamName)
                .collect(Collectors.toList());
        examCombox.setItems(FXCollections.observableArrayList(exams));

        List<String> students = studentDB.getAll().stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        studentCombox.setItems(FXCollections.observableArrayList(students));
    }

    private void setupTableColumns() {
        studentColumn.setCellValueFactory(cellData -> {
            Student student = studentDB.queryByKey(cellData.getValue().getStudentId());
            return student != null ? new SimpleStringProperty(student.getName()) : new SimpleStringProperty("N/A");
        });

        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalScore"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));
    }

    private void setupCharts() {
        // Setup Bar Chart
        barChart.setTitle("Average Scores by Course");
        categoryAxisBar.setLabel("Course");
        numberAxisBar.setLabel("Average Score");

        // Setup Line Chart
        lineChart.setTitle("Score Progression");
        categoryAxisLine.setLabel("Exam");
        numberAxisLine.setLabel("Average Score");

        // Setup Pie Chart
        pieChart.setTitle("Student Score Distribution");
    }

    private void loadGradeData() {
        gradeList.clear();
        List<StudentGradeData> allGrades = gradeDB.getAll();
        gradeList.addAll(allGrades);
        gradeTable.setItems(gradeList);
    }

    private void loadChart() {
        // Clear existing chart data
        barChart.getData().clear();
        pieChart.getData().clear();
        lineChart.getData().clear();

        updateBarChart();
        updatePieChart();
        updateLineChart();
    }

    private void updateBarChart() {
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.setName("Average Scores");

        // Calculate average scores by course
        Map<String, Double> courseAverages = gradeList.stream()
                .collect(Collectors.groupingBy(
                        StudentGradeData::getCourseId,
                        Collectors.averagingDouble(grade -> Integer.parseInt(grade.getTotalScore()))
                ));

        courseAverages.forEach((course, avg) ->
                seriesBar.getData().add(new XYChart.Data<>(course, avg)));

        barChart.getData().add(seriesBar);
    }

    private void updatePieChart() {
        // Clear existing data
        pieChart.getData().clear();

        // Group students by score ranges
        Map<String, Long> scoreDistribution = gradeList.stream()
                .collect(Collectors.groupingBy(
                        grade -> {
                            int score = Integer.parseInt(grade.getTotalScore());
                            if (score >= 90) return "90-100";
                            else if (score >= 80) return "80-89";
                            else if (score >= 70) return "70-79";
                            else if (score >= 60) return "60-69";
                            else return "Below 60";
                        },
                        Collectors.counting()
                ));

        scoreDistribution.forEach((range, count) ->
                pieChart.getData().add(new PieChart.Data(range, count)));
    }

    private void updateLineChart() {
        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.setName("Scores Over Time");

        // Calculate average scores by exam
        Map<String, Double> examAverages = gradeList.stream()
                .collect(Collectors.groupingBy(
                        StudentGradeData::getExamName,
                        Collectors.averagingDouble(grade -> Integer.parseInt(grade.getTotalScore()))
                ));

        examAverages.forEach((exam, avg) ->
                seriesLine.getData().add(new XYChart.Data<>(exam, avg)));

        lineChart.getData().add(seriesLine);
    }

    @FXML
    public void reset() {
        courseCombox.setValue(null);
        examCombox.setValue(null);
        studentCombox.setValue(null);
        loadGradeData();
        loadChart();
    }

    @FXML
    public void query() {
        String selectedCourse = courseCombox.getValue();
        String selectedExam = examCombox.getValue();
        String selectedStudent = studentCombox.getValue();

        ObservableList<StudentGradeData> filteredList = gradeList.filtered(grade ->
                (selectedCourse == null || grade.getCourseId().equals(selectedCourse)) &&
                        (selectedExam == null || grade.getExamName().equals(selectedExam)) &&
                        (selectedStudent == null || grade.getStudentId().equals(selectedStudent))
        );

        gradeTable.setItems(filteredList);

        // Update charts based on filtered data
        if (filteredList.isEmpty()) {
            // Clear charts if no data is available
            barChart.getData().clear();
            pieChart.getData().clear();
            lineChart.getData().clear();
        } else {
            // Load charts with filtered data
            updateBarChart();
            updatePieChart();
            updateLineChart();
        }
    }

    @FXML
    public void refresh() {
        loadGradeData(); // Reload the grade data
        loadChart(); // Reload the charts
    }
}