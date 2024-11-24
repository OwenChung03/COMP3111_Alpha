package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

/**
 * Represents a question in the examination system.
 * This class encapsulates the details of a question, including its content, options,
 * correct answer, type, score, and a reference ID.
 */
public class Question extends Entity {
    private String questionContent; // The content of the question
    private String optionA;         // The text for option A
    private String optionB;         // The text for option B
    private String optionC;         // The text for option C
    private String optionD;         // The text for option D
    private String answer;          // The correct answer to the question
    private String type;            // The type of the question (e.g., multiple choice, true/false)
    private String score;           // The score associated with the question
    private String referID;         // Reference ID for the question

    /**
     * Default constructor for Question.
     * Initializes a new instance of Question with default values.
     */
    public Question() {
        super();
    }

    /**
     * Constructs a Question with the specified details.
     *
     * @param questionContent The content of the question.
     * @param optionA        The text for option A.
     * @param optionB        The text for option B.
     * @param optionC        The text for option C.
     * @param optionD        The text for option D.
     * @param answer         The correct answer to the question.
     * @param type           The type of the question.
     * @param score          The score associated with the question.
     * @param referID        The reference ID for the question.
     */
    public Question(String questionContent, String optionA, String optionB, String optionC, String optionD, String answer, String type, String score, String referID) {
        this.questionContent = questionContent;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.type = type;
        this.score = score;
        this.referID = referID;
    }

    /**
     * Constructs a Question with the specified details,
     * defaulting the reference ID to "0".
     *
     * @param questionContent The content of the question.
     * @param optionA        The text for option A.
     * @param optionB        The text for option B.
     * @param optionC        The text for option C.
     * @param optionD        The text for option D.
     * @param answer         The correct answer to the question.
     * @param type           The type of the question.
     * @param score          The score associated with the question.
     */
    public Question(String questionContent, String optionA, String optionB, String optionC, String optionD, String answer, String type, String score) {
        this.questionContent = questionContent;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.type = type;
        this.score = score;
        this.referID = "0"; // Default reference ID
    }

    // Getters and setters

    /**
     * Returns the content of the question.
     *
     * @return The question content.
     */
    public String getQuestionContent() {
        return questionContent;
    }

    /**
     * Sets the content of the question.
     *
     * @param questionContent The content to set for the question.
     */
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    /**
     * Returns the text for option A.
     *
     * @return The text for option A.
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * Sets the text for option A.
     *
     * @param optionA The text to set for option A.
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /**
     * Returns the text for option B.
     *
     * @return The text for option B.
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * Sets the text for option B.
     *
     * @param optionB The text to set for option B.
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    /**
     * Returns the text for option C.
     *
     * @return The text for option C.
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * Sets the text for option C.
     *
     * @param optionC The text to set for option C.
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    /**
     * Returns the text for option D.
     *
     * @return The text for option D.
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * Sets the text for option D.
     *
     * @param optionD The text to set for option D.
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    /**
     * Returns the correct answer to the question.
     *
     * @return The correct answer.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the correct answer to the question.
     *
     * @param answer The answer to set.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Returns the type of the question.
     *
     * @return The type of the question.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the question.
     *
     * @param type The type to set for the question.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the score associated with the question.
     *
     * @return The score.
     */
    public String getScore() {
        return score;
    }

    /**
     * Sets the score associated with the question.
     *
     * @param score The score to set.
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * Returns the reference ID for the question.
     *
     * @return The reference ID.
     */
    public String getreferID() {
        return referID;
    }

    /**
     * Sets the reference ID for the question.
     *
     * @param referID The reference ID to set.
     */
    public void setreferID(String referID) {
        this.referID = referID;
    }
}