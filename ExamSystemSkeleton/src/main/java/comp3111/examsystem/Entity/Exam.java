package comp3111.examsystem.Entity;

import comp3111.examsystem.tools.Entity;

import java.util.ArrayList;
import java.util.List;

public class Exam extends Entity {
    private String ExamName;
    private Long CourseID;
    private Integer ExamTime;
    private Boolean Publish;
    private List<Long> questionKeys; // List to hold questions for the exam

        public Exam() {
            super();
        }

        public Exam(String examName, Long courseID, Integer examTime, Boolean publish,List<Long> questionKeys) {
            this.ExamName = examName;
            this.CourseID = courseID;
            this.ExamTime = examTime;
            this.Publish = publish;
            this.questionKeys = questionKeys; // Initialize the list
        }

        // Getters and setters for other fields
        public String getExamName() {
            return ExamName;
        }

        public void setExamName(String examName) {
            this.ExamName = examName;
        }

        public Long getCourseID() {
            return CourseID;
        }

        public void setCourseID(Long courseID) {
            this.CourseID = courseID;
        }

        public Integer getExamTime() {
            return ExamTime;
        }

        public void setExamTime(Integer examTime) {
            this.ExamTime = examTime;
        }

        public Boolean getPublish() {
            return Publish;
        }

        public void setPublish(Boolean publish) {
            this.Publish = publish;
        }

        public List<Long> questionKeys() {
            return questionKeys;
        }

        public void setQuestions(List<Long> questionKeys) {
            this.questionKeys = questionKeys;
        }
}
