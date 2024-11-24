package comp3111.examsystem.entity;

import comp3111.examsystem.tools.Entity;

/**
 * Represents an exam in the examination system.
 * This class encapsulates details about the exam, including its name, associated course,
 * duration, publish status, and related question keys.
 */
public class Exam extends Entity {

    private String examName;     // Name of the exam
    private String courseKey;     // Key of the course associated with the exam
    private String examTime;      // Duration of the exam
    private String publish;       // Publish status of the exam
    private String questionKeys;  // Keys of the questions associated with the exam

    /**
     * Default constructor for Exam.
     * Initializes a new instance of Exam with default values.
     */
    public Exam() {
        super();
    }

    /**
     * Constructs an Exam with the specified details.
     *
     * @param examName   The name of the exam.
     * @param courseKey  The key of the course associated with the exam.
     * @param examTime   The duration of the exam.
     * @param publish    The publish status of the exam.
     * @param questionKeys The keys of questions associated with the exam.
     */
    public Exam(String examName, String courseKey, String examTime, String publish, String questionKeys) {
        this.examName = examName;
        this.courseKey = courseKey;
        this.examTime = examTime;
        this.publish = publish;
        this.questionKeys = questionKeys;
    }

    /**
     * Returns the name of the exam.
     *
     * @return The exam name.
     */
    public String getExamName() {
        return examName;
    }

    /**
     * Sets the name of the exam.
     *
     * @param examName The exam name to set.
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * Returns the key of the course associated with the exam.
     *
     * @return The course key.
     */
    public String getCourseKey() {
        return courseKey;
    }

    /**
     * Sets the key of the course associated with the exam.
     *
     * @param courseKey The course key to set.
     */
    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }

    /**
     * Returns the duration of the exam.
     *
     * @return The exam time.
     */
    public String getExamTime() {
        return examTime;
    }

    /**
     * Sets the duration of the exam.
     *
     * @param examTime The exam time to set.
     */
    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    /**
     * Returns the publish status of the exam.
     *
     * @return The publish status.
     */
    public String getPublish() {
        return publish;
    }

    /**
     * Sets the publish status of the exam.
     *
     * @param publish The publish status to set.
     */
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /**
     * Returns the keys of questions associated with the exam.
     *
     * @return The question keys.
     */
    public String getQuestionKeys() {
        return questionKeys;
    }

    /**
     * Sets the keys of questions associated with the exam.
     *
     * @param questionKeys The question keys to set.
     */
    public void setQuestionKeys(String questionKeys) {
        this.questionKeys = questionKeys;
    }
}