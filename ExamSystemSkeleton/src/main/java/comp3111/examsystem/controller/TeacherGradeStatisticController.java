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

public class TeacherGradeStatisticController implements Initializable {
    public static class GradeExampleClass {
        public String getStudentName() {
            return "student";
        }
        public String getCourseNum() {
            return "comp3111";
        }
        public String getExamName() {
            return "final";
        }
        public String getScore() {
            return "100";
        }
        public String getFullScore() {
            return "100";
        }
        public String getTimeSpend() {
            return "60";
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barChart.setLegendVisible(false);
        categoryAxisBar.setLabel("Course");
        numberAxisBar.setLabel("Avg. Score");
        pieChart.setLegendVisible(false);
        pieChart.setTitle("Student Scores");
        lineChart.setLegendVisible(false);
        categoryAxisLine.setLabel("Exam");
        numberAxisLine.setLabel("Avg. Score");

        gradeList.add(new GradeExampleClass());
        gradeTable.setItems(gradeList);
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseNum"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpend"));

        refresh();
        loadChart();
    }

    @FXML
    public void refresh() {
    }

    private void loadChart() {
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        seriesBar.getData().clear();
        barChart.getData().clear();
        for (int i = 0;  i < 5; i++) {
            seriesBar.getData().add(new XYChart.Data<>("COMP" + i, 50));
        }
        barChart.getData().add(seriesBar);

        pieChart.getData().clear();
        for (int i = 0;  i < 4; i++) {
            pieChart.getData().add(new PieChart.Data("student" + i, 80));
        }

        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.getData().clear();
        lineChart.getData().clear();
        for (int i = 0;  i < 6; i++) {
            seriesLine.getData().add(new XYChart.Data<>("COMP3111" + "-" + "quiz" + i, 70));
        }
        lineChart.getData().add(seriesLine);

    }

    @FXML
    public void reset() {
    }

    @FXML
    public void query() {
    }
}
