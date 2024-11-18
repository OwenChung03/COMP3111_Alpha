package comp3111.examsystem.controller;


import comp3111.examsystem.entity.Student;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.tools.MsgSender;
import comp3111.examsystem.tools.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizLoader {

    // Method to load questions from the quiz.txt file
    public List<Question> loadQuestionsFromFile(String filePath) throws IOException {
        List<Question> questions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            // Parse a single question from a line
            Question question = parseQuestion(line);
            if (question != null) {
                questions.add(question);
            }
        }

        reader.close();
        return questions;
    }

    // Method to parse a single question from a line
    private Question parseQuestion(String line) {
        String[] entries = line.split(",");
        String questionContent = null, optionA = null, optionB = null, optionC = null, optionD = null;
        String answer = null, type = null, score = null;

        for (String entry : entries) {
            String[] keyValue = entry.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "QuestionContent":
                        questionContent = value;
                        break;
                    case "OptionA":
                        optionA = value;
                        break;
                    case "OptionB":
                        optionB = value;
                        break;
                    case "OptionC":
                        optionC = value;
                        break;
                    case "OptionD":
                        optionD = value;
                        break;
                    case "Answer":
                        answer = value;
                        break;
                    case "Type":
                        type = value;
                        break;
                    case "Score":
                        score = value;
                        break;
                    default:
                        System.out.println("Unknown field: " + key);
                }
            }
        }

        // Create the Question object using the constructor from your friend's implementation
        if (questionContent != null && optionA != null && optionB != null && optionC != null &&
                optionD != null && answer != null && type != null && score != null) {

            return new Question(questionContent, optionA, optionB, optionC, optionD, answer, type, score);
        }

        return null;  // Return null if parsing failed
    }
}