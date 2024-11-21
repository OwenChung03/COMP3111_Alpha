package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Course;
import comp3111.examsystem.tools.Database;

import java.util.ArrayList;
import java.util.List;

public class CourseLoader {

    // Load courses from the course.txt file
    public List<Course> loadCoursesFromDatabase() {
        Database<Course> courseDatabase = new Database<>(Course.class); // Assuming Course has a no-arg constructor
        return courseDatabase.getAll();
    }

    // Load a specific course by its ID
    public Course loadCourseById(Long courseId) {
        Database<Course> courseDatabase = new Database<>(Course.class);
        return courseDatabase.queryByKey(courseId.toString());
    }

    // Helper method to convert List<Long> to List<String>
    private List<String> convertToStringList(List<Long> longList) {
        List<String> stringList = new ArrayList<>();
        for (Long l : longList) {
            stringList.add(l.toString());
        }
        return stringList;
    }
}