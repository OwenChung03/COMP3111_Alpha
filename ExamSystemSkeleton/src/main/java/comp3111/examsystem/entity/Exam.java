package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;



import java.util.ArrayList;
import java.util.List;

public class Exam extends Entity {

    private String examName;
    private String courseKey;
    private String examTime;
    private String publish;
    private String questionKeys;

    // Constructor

    public Exam() {
        super();
    }


    public Exam(String examName, String courseKey, String examTime, String publish, String questionKeys) {
         this.examName = examName;
        this.courseKey = courseKey;
        this.examTime = examTime;
        this.publish = publish;
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

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getQuestionKeys() {
        return questionKeys;
    }

    public void setQuestionKeys(String questionKeys) {
        this.questionKeys = questionKeys;
    }


}
