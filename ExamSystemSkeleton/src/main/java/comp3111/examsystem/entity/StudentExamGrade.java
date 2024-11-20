package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;


public class StudentExamGrade extends Entity {
    private String studentId;
    private String examId;
    private String grade;

    // Constructor
    public StudentExamGrade(String studentId, String examId, String grade) {
        this.studentId = studentId;
        this.examId = examId;
        this.grade = grade;
    }

    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}