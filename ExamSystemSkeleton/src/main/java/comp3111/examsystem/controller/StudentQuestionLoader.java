//package comp3111.examsystem.controller;
//
//import java.io.*;
//import java.util.*;
//
//public class StudentQuestionLoader {
//
//    private static final String QUESTIONS_FILE = "questions.txt"; // The file path where questions are stored
//
//    // Data structure to hold questions
//    private List<Question> questionsList;
//
//    public QuestionLoader() {
//        questionsList = new ArrayList<>();
//        loadQuestionsFromFile();
//    }
//
//    // Method to read questions from the file
//    private void loadQuestionsFromFile() {
//        try (BufferedReader br = new BufferedReader(new FileReader(QUESTIONS_FILE))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] fields = line.split("\\|");  // Assuming '|' is the delimiter
//                if (fields.length == 8) {
//                    int questionId = Integer.parseInt(fields[0]);
//                    String questionText = fields[1];
//                    String optionA = fields[2];
//                    String optionB = fields[3];
//                    String optionC = fields[4];
//                    String optionD = fields[5];
//                    String correctOption = fields[6];
//                    int score = Integer.parseInt(fields[7]);
//
//                    // Create a Question object and add to the list
//                    Question question = new Question(questionId, questionText, optionA, optionB, optionC, optionD, correctOption, score);
//                    questionsList.add(question);
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading questions file: " + e.getMessage());
//        }
//    }
//
//    // Getter method to return the list of questions
//    public List<Question> getQuestions() {
//        return questionsList;
//    }
//}
