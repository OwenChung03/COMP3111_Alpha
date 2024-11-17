package comp3111.examsystem.Entity;

import comp3111.examsystem.tools.Entity;

public class Teacher extends Entity {
    private String username;
    private String name;
    private String gender;
    private String age;
    private String position;
    private String department;
    private String password;

    //constructor
    public Teacher() {
        super();
    }

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
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

