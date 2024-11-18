package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Exam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamLoader {

    public List<Exam> loadExamsFromFile(String filePath) throws IOException {
        List<Exam> exams = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] entries = line.split(",");
            String examName = "";
            long courseKey = 0;
            int examTime = 0;
            boolean publishStatus = false;
            List<Long> questionKeys = new ArrayList<>();
            long examID = 0;

            for (String entry : entries) {
                String[] keyValue = entry.split(":");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "examName":
                        examName = value;
                        break;
                    case "courseKey":
                        courseKey = Long.parseLong(value);
                        break;
                    case "examTime":
                        examTime = Integer.parseInt(value);
                        break;
                    case "publishStatus":
                        publishStatus = Boolean.parseBoolean(value);
                        break;
                    case "questionKey":
                        questionKeys = parseQuestionKeys(value);
                        break;
                    case "id":
                        examID = Long.parseLong(value);
                        break;
                }
            }

            exams.add(new Exam(examName, courseKey, examTime, publishStatus, questionKeys, examID));
        }

        reader.close();
        return exams;
    }

    // Helper method to parse question keys
    private List<Long> parseQuestionKeys(String questionKeysString) {
        String[] questionKeyArray = questionKeysString.split("/");
        List<Long> questionKeys = new ArrayList<>();
        for (String key : questionKeyArray) {
            questionKeys.add(Long.parseLong(key));
        }
        return questionKeys;
    }
}