package comp3111.examsystem.Entity;

import comp3111.examsystem.tools.Entity;

import javax.swing.text.html.Option;

public class Question extends Entity {
    private String QuestionContent;
    private String OptionA;
    private String OptionB;
    private String OptionC;
    private String OptionD;
    private String Answer;
    private String Type;
    private String Score;

    public Question() {
        super();
    }
    public Question(String QuestionContent, String OptionA, String OptionB, String OptionC, String OptionD, String Answer, String Type, String Score) {
        this.QuestionContent = QuestionContent;
        this.OptionB = OptionB;
        this.OptionA = OptionA;
        this.OptionC = OptionC;
        this.OptionD = OptionD;
        this.Answer = Answer;
        this.Type = Type;
        this.Score = Score;
    }

    public String getQuestionContent() {
        return QuestionContent;
    }
    public String getOptionA() {
        return OptionA;
    }
    public String getOptionB() {return OptionB;}
    public String getOptionC() {
        return OptionC;
    }
    public String getOptionD() {
        return OptionD;
    }
    public String getAnswer() {return Answer;}
    public String getType() {return Type;}
    public String getScore() {return Score;}

    public void setQuestionContent() {
        this.QuestionContent = QuestionContent;
    }
    public void setOptionA() {
        this.OptionA = OptionA;
    }
    public void setOptionB() {this.OptionB = OptionB;}
    public void setOptionC() {
        this.OptionC = OptionC;
    }
    public void setOptionD() {
        this.OptionD = OptionD;
    }
    public void setAnswer() {this.Answer = Answer;}
    public void setType() {this.Type = Type;}
    public void setScore() {this.Score = Score;}
}
