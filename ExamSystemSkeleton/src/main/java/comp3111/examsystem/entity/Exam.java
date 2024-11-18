package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

import java.util.ArrayList;
import java.util.List;

public class Exam extends Entity {

    private String examName;
    private long courseKey;
    private int examTime;
    private boolean publishStatus;
    private List<Long> questionKeys;

    // Constructor

    public Exam() {
        super();
    }


    public Exam(String examName, long courseKey, int examTime, boolean publishStatus, List<Long> questionKeys, Long id) {
        super(id); // Call the Entity constructor to set the ID
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

    public long getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(long courseKey) {
        this.courseKey = courseKey;
    }

    public int getExamTime() {
        return examTime;
    }

    public void setExamTime(int examTime) {
        this.examTime = examTime;
    }

    public boolean isPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(boolean publishStatus) {
        this.publishStatus = publishStatus;
    }

    public List<Long> getQuestionKeys() {
        return questionKeys;
    }

    public void setQuestionKeys(List<Long> questionKeys) {
        this.questionKeys = questionKeys;
    }
}