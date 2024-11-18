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

public class QuestionLoader {

    // Load questions from the database based on a list of question IDs
    public List<Question> loadQuestionsByIds(List<String> questionIds) {
        Database<Question> questionDatabase = new Database<>(Question.class);
        return questionDatabase.queryByKeys(questionIds);
    }

}