package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

/**
 * Represents a course in the examination system.
 * This class encapsulates the course ID, course name, and department information.
 */
public class Course extends Entity {
    private String courseId;      // Unique identifier for the course
    private String courseName;    // Name of the course
    private String department;     // Department offering the course

    /**
     * Default constructor for Course.
     * Initializes a new instance of Course with default values.
     */
    public Course() {
        super();
    }

    /**
     * Constructs a Course with the specified course ID, name, and department.
     *
     * @param courseId   The unique identifier for the course.
     * @param courseName The name of the course.
     * @param department  The department offering the course.
     */
    public Course(String courseId, String courseName, String department) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.department = department;
    }

    /**
     * Returns the unique identifier for the course.
     *
     * @return The course ID.
     */
    public String getCourseId() {
        return courseId;
    }

    /**
     * Sets the unique identifier for the course.
     *
     * @param courseId The course ID to set.
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Returns the name of the course.
     *
     * @return The course name.
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the course.
     *
     * @param courseName The course name to set.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Returns the department offering the course.
     *
     * @return The department name.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department offering the course.
     *
     * @param department The department name to set.
     */
    public void setDepartment(String department) {
        this.department = department;
    }
}