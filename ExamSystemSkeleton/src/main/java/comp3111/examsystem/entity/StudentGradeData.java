package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

public class StudentGradeData extends Entity{
    private String studentId;
    private String courseId;
    private String examName;
    private String totalScore;
    private String fullScore;
    private String timeSpent;  // New field to store the time spent

    public StudentGradeData() {
        super();
    }

    // Constructor
    public StudentGradeData(String studentId, String courseId, String examName, String totalScore, String fullScore, String timeSpent) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.examName = examName;
        this.totalScore = totalScore;
        this.fullScore = fullScore;
        this.timeSpent = timeSpent;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

}