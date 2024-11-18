package comp3111.examsystem.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    private final StringProperty courseId;
    private final StringProperty courseName;
    private final StringProperty department;

    public Course(String courseId, String courseName, String department) {
        this.courseId = new SimpleStringProperty(courseId);
        this.courseName = new SimpleStringProperty(courseName);
        this.department = new SimpleStringProperty(department);
    }

    public String getCourseId() {
        return courseId.get();
    }

    public void setCourseId(String courseId) {
        this.courseId.set(courseId);
    }

    public StringProperty courseIdProperty() {
        return courseId;
    }

    public String getCourseName() {
        return courseName.get();
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public StringProperty departmentProperty() {
        return department;
    }
}