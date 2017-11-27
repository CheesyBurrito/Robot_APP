package ca.usherbrooke.www.vrohms.Models;

/**
 * Created by hugo on 2017-11-26.
 */

public class Question
{
    private String statement;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    public Question()
    {
        this.statement = null;
        this.answerA = null;
        this.answerB = null;
        this.answerC = null;
        this.answerD = null;
    }

    public Question(String statement, String[] answers)
    {
        this.statement = statement;
        this.answerA = answers[0];
        this.answerB = answers[1];
        this.answerC = answers[2];
        this.answerD = answers[3];
    }

    public Boolean isQuestionComplete()
    {
        return statement != null
            && answerA != null
            && answerB != null
            && answerC != null
            && answerD != null;
    }

    public String getStatement()
    {
        return statement;
    }

    public void setStatement(String statement)
    {
        this.statement = statement;
    }

    public String getAnswerA()
    {
        return answerA;
    }

    public void setAnswerA(String answerA)
    {
        this.answerA = answerA;
    }

    public String getAnswerB()
    {
        return answerB;
    }

    public void setAnswerB(String answerB)
    {
        this.answerB = answerB;
    }

    public String getAnswerC()
    {
        return answerC;
    }

    public void setAnswerC(String answerC)
    {
        this.answerC = answerC;
    }

    public String getAnswerD()
    {
        return answerD;
    }

    public void setAnswerD(String answerD)
    {
        this.answerD = answerD;
    }
}
