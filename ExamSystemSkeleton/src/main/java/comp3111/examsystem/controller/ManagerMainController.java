package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerMainController implements Initializable {
    @FXML
    private VBox mainbox;

    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void openStudentManageUI() {
    }

    @FXML
    public void openTeacherManageUI() {
    }

    @FXML
    public void openCourseManageUI() {
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
}
