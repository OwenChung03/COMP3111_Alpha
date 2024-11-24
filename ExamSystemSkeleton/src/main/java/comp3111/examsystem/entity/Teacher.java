package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

/**
 * Represents a teacher in the examination system.
 * This class encapsulates the teacher's details, including their username, name,
 * gender, age, position, department, and password.
 */
public class Teacher extends Entity {
    private String username;    // Username of the teacher
    private String name;        // Name of the teacher
    private String gender;      // Gender of the teacher
    private String age;         // Age of the teacher
    private String position;    // Position of the teacher
    private String department;   // Department of the teacher
    private String password;     // Password for the teacher's account

    /**
     * Default constructor for Teacher.
     * Initializes a new instance of Teacher with default values.
     */
    public Teacher() {
        super();
    }

    /**
     * Constructs a Teacher with the specified details.
     *
     * @param username   The username of the teacher.
     * @param name       The name of the teacher.
     * @param gender     The gender of the teacher.
     * @param age        The age of the teacher.
     * @param position    The position of the teacher.
     * @param department  The department of the teacher.
     * @param password   The password for the teacher's account.
     */
    public Teacher(String username, String name, String gender, String age, String position, String department, String password) {
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.position = position;
        this.department = department;
        this.password = password;
    }

    // Getters and Setters

    /**
     * Returns the username of the teacher.
     *
     * @return The teacher's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the teacher.
     *
     * @param username The username to set for the teacher.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the name of the teacher.
     *
     * @return The teacher's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the teacher.
     *
     * @param name The name to set for the teacher.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the gender of the teacher.
     *
     * @return The teacher's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the teacher.
     *
     * @param gender The gender to set for the teacher.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the position of the teacher.
     *
     * @return The teacher's position.
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the teacher.
     *
     * @param position The position to set for the teacher.
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Returns the age of the teacher.
     *
     * @return The teacher's age.
     */
    public String getAge() {
        return age;
    }

    /**
     * Sets the age of the teacher.
     *
     * @param age The age to set for the teacher.
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Returns the department of the teacher.
     *
     * @return The teacher's department.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department of the teacher.
     *
     * @param department The department to set for the teacher.
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Returns the password of the teacher.
     *
     * @return The teacher's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the teacher's account.
     *
     * @param password The password to set for the teacher.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}