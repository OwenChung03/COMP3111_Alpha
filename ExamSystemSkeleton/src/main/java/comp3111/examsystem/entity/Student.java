package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

public class Student extends Entity {
    private String name;
    private String gender;
    private int age;
    private String department;
    private String password;

    // Constructor
    public Student(Long id, String name, String gender, int age, String department, String password) {
        super(id);
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.department = department;
        this.password = password;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}