package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentMainController implements Initializable {
    @FXML
    ComboBox<String> examCombox;

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void openExamUI() {
    }

    @FXML
    public void openGradeStatistic() {
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
}
