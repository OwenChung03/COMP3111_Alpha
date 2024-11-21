package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;



import java.util.ArrayList;
import java.util.List;

public class Exam extends Entity {

    private String examName;
    private String courseKey;
    private String examTime;
    private String publishStatus;
    private String questionKeys;

    // Constructor

    public Exam() {
        super();
    }


    public Exam(String examName, String courseKey, String examTime, String publishStatus, String questionKeys) {
        this.examName = examName;
        this.courseKey = courseKey;
        this.examTime = examTime;
        this.publishStatus = publishStatus;
        this.questionKeys = questionKeys;
    }

    // Getters and setters
    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String isPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getQuestionKeys() {
        return questionKeys;
    }

    public void setQuestionKeys(String questionKeys) {
        this.questionKeys = questionKeys;
    }
}
