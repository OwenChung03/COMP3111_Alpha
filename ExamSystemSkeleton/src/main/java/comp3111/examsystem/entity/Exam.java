package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

import java.util.ArrayList;
import java.util.List;

public class Exam extends Entity {
    private String ExamName;
    private String CourseID;
    private String ExamTime;
    private String Publish;
    private List<Question> questions; // List to hold questions for the exam

    public Exam() {
        super();
    }

    public Exam(String examName, String courseID, String examTime, String publish) {
        this.ExamName = examName;
        this.CourseID = courseID;
        this.ExamTime = examTime;
        this.Publish = publish;
        this.questions = new ArrayList<>(); // Initialize the list
    }

    // Getters and setters for other fields
    public String getExamName() {
        return ExamName;
    }

    public void setExamName(String examName) {
        this.ExamName = examName;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        this.CourseID = courseID;
    }

    public String getExamTime() {
        return ExamTime;
    }

    public void setExamTime(String examTime) {
        this.ExamTime = examTime;
    }

    public String getPublish() {
        return Publish;
    }

    public void setPublish(String publish) {
        this.Publish = publish;
    }

    // Methods to manage questions
    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}