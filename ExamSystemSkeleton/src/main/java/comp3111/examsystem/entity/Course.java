package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course extends Entity {
    private String courseId;
    private String courseName;
    private String department;
    public Course() {
        super();
    }
    public Course(String courseId, String courseName, String department) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
    }

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


}