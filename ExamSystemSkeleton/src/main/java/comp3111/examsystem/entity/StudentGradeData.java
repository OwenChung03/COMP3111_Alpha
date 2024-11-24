package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

/**
 * Represents the grade data for a student in the examination system.
 * This class encapsulates information about the student's performance in a specific exam,
 * including their scores and the time spent on the exam.
 */
public class StudentGradeData extends Entity {
    private String studentId;    // The ID of the student
    private String courseId;     // The ID of the course
    private String examName;     // The name of the exam
    private String totalScore;    // The total score achieved by the student
    private String fullScore;      // The maximum possible score for the exam
    private String timeSpent;      // The time spent by the student on the exam

    /**
     * Default constructor for StudentGradeData.
     * Initializes a new instance of StudentGradeData with default values.
     */
    public StudentGradeData() {
        super();
    }

    /**
     * Constructs a StudentGradeData with the specified details.
     *
     * @param studentId  The ID of the student.
     * @param courseId   The ID of the course.
     * @param examName   The name of the exam.
     * @param totalScore  The total score achieved by the student.
     * @param fullScore   The maximum possible score for the exam.
     * @param timeSpent   The time spent by the student on the exam.
     */
    public StudentGradeData(String studentId, String courseId, String examName, String totalScore, String fullScore, String timeSpent) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.examName = examName;
        this.totalScore = totalScore;
        this.fullScore = fullScore;
        this.timeSpent = timeSpent;
    }

    // Getters and Setters

    /**
     * Returns the ID of the student.
     *
     * @return The student's ID.
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Sets the ID of the student.
     *
     * @param studentId The student ID to set.
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Returns the ID of the course.
     *
     * @return The course ID.
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the ID of the course.
     *
     * @param courseId The course ID to set.
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the name of the exam.
     *
     * @return The exam name.
     */
    public String getExamName() {
        return examName;
    }

    /**
     * Sets the name of the exam.
     *
     * @param examName The exam name to set.
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * Returns the total score achieved by the student.
     *
     * @return The total score.
     */
    public String getTotalScore() {
        return totalScore;
    }

    /**
     * Sets the total score achieved by the student.
     *
     * @param totalScore The total score to set.
     */
    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Returns the maximum possible score for the exam.
     *
     * @return The full score.
     */
    public String getFullScore() {
        return fullScore;
    }

    /**
     * Sets the maximum possible score for the exam.
     *
     * @param fullScore The full score to set.
     */
    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    /**
     * Returns the time spent by the student on the exam.
     *
     * @return The time spent.
     */
    public String getTimeSpent() {
        return timeSpent;
    }

    /**
     * Sets the time spent by the student on the exam.
     *
     * @param timeSpent The time spent to set.
     */
    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }
}