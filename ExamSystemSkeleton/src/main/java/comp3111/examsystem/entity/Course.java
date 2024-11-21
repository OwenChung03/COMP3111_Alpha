package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

public class Course extends Entity {
    private String courseId;
    private String courseName;
    private String department;

    // Default constructor
    public Course() {
        super();
    }

    // Constructor with parameters
    public Course(String courseId, String courseName, String department) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
    }

    // Getters and setters
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", courseDepartment='" + department + '\'' +
                ", id=" + id +
                '}';
    }

}