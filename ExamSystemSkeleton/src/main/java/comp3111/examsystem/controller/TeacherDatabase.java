package comp3111.examsystem.controller;

import comp3111.examsystem.controller.TeacherLoginController;

import java.util.ArrayList;
import java.util.List;

public class TeacherDatabase {
    private static List<TeacherLoginController.Teacher> teachers = new ArrayList<>();

    public static List<TeacherLoginController.Teacher> getTeachers() {
        return teachers;
    }

    public static void addTeacher(TeacherLoginController.Teacher teacher) {
        teachers.add(teacher);
    }
}