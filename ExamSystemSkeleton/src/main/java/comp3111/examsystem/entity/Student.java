package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

/**
 * Represents a student in the examination system.
 * This class encapsulates the student's details, including their username, name,
 * gender, age, department, and password.
 */
public class Student extends Entity {
    private String username;    // Username of the student
    private String name;        // Name of the student
    private String gender;      // Gender of the student
    private String age;         // Age of the student
    private String department;   // Department of the student
    private String password;     // Password for the student's account

    /**
     * Default constructor for Student.
     * Initializes a new instance of Student with default values.
     */
    public Student() {
        super();
    }

    /**
     * Constructs a Student with the specified details.
     *
     * @param id         The unique identifier for the student.
     * @param username   The username of the student.
     * @param name       The name of the student.
     * @param gender     The gender of the student.
     * @param age        The age of the student.
     * @param department  The department of the student.
     * @param password   The password for the student's account.
     */
    public Student(Long id, String username, String name, String gender, String age, String department, String password) {
        super(id);
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.department = department;
        this.password = password;
    }

    // Getters and Setters

    /**
     * Returns the username of the student.
     *
     * @return The student's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the student.
     *
     * @param username The username to set for the student.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the name of the student.
     *
     * @return The student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the student.
     *
     * @param name The name to set for the student.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the gender of the student.
     *
     * @return The student's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the student.
     *
     * @param gender The gender to set for the student.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the age of the student.
     *
     * @return The student's age.
     */
    public String getAge() {
        return age;
    }

    /**
     * Sets the age of the student.
     *
     * @param age The age to set for the student.
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Returns the department of the student.
     *
     * @return The student's department.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department of the student.
     *
     * @param department The department to set for the student.
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Returns the password of the student.
     *
     * @return The student's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the student's account.
     *
     * @param password The password to set for the student.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}