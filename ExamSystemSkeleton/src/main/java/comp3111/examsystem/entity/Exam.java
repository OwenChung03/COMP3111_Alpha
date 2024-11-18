package comp3111.examsystem.entity;

import java.util.List;

public class Exam {
    private String examName;
    private long courseKey;
    private int examTime;
    private boolean publishStatus;
    private List<Long> questionKeys;
    private long examID;

    public Exam(String examName, long courseKey, int examTime, boolean publishStatus, List<Long> questionKeys, long examID) {
        this.examName = examName;
        this.courseKey = courseKey;
        this.examTime = examTime;
        this.publishStatus = publishStatus;
        this.questionKeys = questionKeys;
        this.examID = examID;
    }

    public String getExamName() {
        return examName;
    }

    public long getCourseKey() {
        return courseKey;
    }

    public int getExamTime() {
        return examTime;
    }

    public boolean isPublishStatus() {
        return publishStatus;
    }

    public List<Long> getQuestionKeys() {
        return questionKeys;
    }

    public long getExamID() {
        return examID;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examName='" + examName + '\'' +
                ", courseKey=" + courseKey +
                ", examTime=" + examTime +
                ", publishStatus=" + publishStatus +
                ", questionKeys=" + questionKeys +
                ", examID=" + examID +
                '}';
    }
}