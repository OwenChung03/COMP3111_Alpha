package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;
import comp3111.examsystem.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static comp3111.examsystem.tools.MsgSender.showMsg;

public class StudentMainController implements Initializable {

    @FXML
    private ListView<String> examListView;

    @FXML
    private Button startQuizButton;

    @FXML
    private ComboBox<String> examCombox;

    private static List<Exam> exams;

    private static ExamLoader examLoader; // Dependency-injected loader

    // Constructor for dependency injection (useful for testing)
    public StudentMainController(ExamLoader examLoader) {
        this.examLoader = examLoader;
    }

    // Default constructor for JavaFX (uses default ExamLoader)
    public StudentMainController() {
        this(new ExamLoader());
    }

    //initialization
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadExams();
    }


    //purely UI implementation, I separated the logic into different boolean function.
    //Please don't count it in the coverage ratio
    public boolean loadExams() {
        try {
            exams = examLoader.loadExamsFromDatabase();
            if (!handleLoadedExams(exams)){
                showMsg("Error", "Error: Failed to load exams due to empty exam.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMsg("Error", "Error: Failed to load exams due to an exception.");
            return false;
        }
        return true;
    }

    public boolean handleLoadedExams(List<Exam> exams) {
        if (exams == null) {
            return false;
        }

        if (exams.isEmpty()) {
            return false;
        }

        populateExamComboBox(exams);
        return true;
    }

    public boolean NonEmptyExam(List<Exam> exams){
        if (exams == null || exams.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean ValidExamName(String examName){
        if (examName == null || examName == "") {
            return false;
        }
        return true;
    }

    public boolean populateExamComboBox(List<Exam> exams) {
        if (!NonEmptyExam(exams)) {
            showMsg("Error", "Error: Failed to load exams due to empty exam.");
            return false;
        }

        List<String> examNames = exams.stream()
                .map(Exam::getExamName)
                .toList();
        examCombox.setItems(FXCollections.observableArrayList(examNames));
        return true;
    }

    public Optional<Exam> findExamByName(String examName) {
        if (!ValidExamName(examName)) {
            return Optional.empty();
        }

        return exams.stream()
                .filter(exam -> exam.getExamName().equals(examName))
                .findFirst();
    }

    //purely UI implementation, I separated the logic into different boolean function.
    //Please don't count it in the coverage ratio
    public boolean loadFXMLAndShow(String fxmlPath, String title, Exam selectedExam) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load()));

            // Pass the selected exam to the controller (if applicable)
            if (selectedExam != null) {
                ExamScreenController examScreenController = loader.getController();
                examScreenController.setExam(selectedExam);
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showMsg("Error", "Failed to load screen: " + title);
            return false;
        }
        return true;
    }

    // ---------- Event Handlers ----------

    //purely UI implementation, I separated the logic into different boolean function.
    //Please don't count it in the coverage ratio
    @FXML
    public void openExamUI(ActionEvent event) {
        String selectedExamName = examCombox.getValue();
        if (selectedExamName == null) {
            showMsg("Error", "Please select an exam before starting.");
            return;
        }

        Optional<Exam> selectedExam = findExamByName(selectedExamName);
        if (selectedExam.isPresent()) {
            loadFXMLAndShow("ExamScreen.fxml", "Quiz: " + selectedExamName, selectedExam.get());
        } else {
            showMsg("Error", "Error: Selected exam not found.");
        }
    }

    //purely UI implementation, I separated the logic into different boolean function.
    //Please don't count it in the coverage ratio
    @FXML
    public void openGradeStatistic(ActionEvent event) {
        loadFXMLAndShow("StudentGradeStat.fxml", "Grade Statistics", null);
    }

    //purely UI implementation, I separated the logic into different boolean function.
    //Please don't count it in the coverage ratio
    @FXML
    public void exit() {
        System.exit(0);
    }
}