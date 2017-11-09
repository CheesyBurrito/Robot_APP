package com.example.william.robot_app;

/**
 * Created by William on 11/9/2017.
 */

public abstract class Question {
    //The categories for the questions: 0 = history, 1 = sport, 2 = science, 3 = geography,
    // 4 = pop-culture, and 5 = random choice
    //Easy has 20 seconds to answer
    //Medium has 15 seconds to answer
    //Hard has 10 seconds to answer


    //The number of categories
    private int numOfCategories = 6;
    //The category of the question
    private int category;
    //The difficulty of the questions 0 = Easy, 1 = Medium, 2 = Hard
    private int questionDifficulty;
    //Stores the number of questions per category in the game
    private int numOfQuestions = 12;
    //Array storing all of the easy questions
    private String[] questionsEasy = new String[numOfQuestions];
    //Array storing all of the medium questions
    private String[] questionsMedium = new String[numOfQuestions];
    //Array storing all of the hard questions
    private String[] questionsHard = new String[numOfQuestions];
    //Array storing all of the easy questions answers
    private String[][] answersEasy = new String[numOfQuestions][4];
    //Array storing all of the medium questions answers
    private String[][] answersMedium = new String[numOfQuestions][4];
    //Array storing all of the hard questions answers
    private String[][] answersHard = new String[numOfQuestions][4];
    //Array storing the index of all the easy questions right answers
    private int[] goodAnswerEasy = new int[numOfQuestions];
    //Array storing the index of all the medium questions right answers
    private int[] goodAnswerMedium = new int[numOfQuestions];
    //Array storing the index of all the hard questions right answers
    private int[] goodAnswerHard = new int[numOfQuestions];
    //A second to determine if the questions were already asked
    private boolean[] questionsAskedEasy = new boolean[numOfQuestions];
    //A second to determine if the questions were already asked
    private boolean[] questionsAskedMedium = new boolean[numOfQuestions];
    //A second to determine if the questions were already asked
    private boolean[] questionsAskedHard = new boolean[numOfQuestions];

    protected abstract void setupQuestions();
    protected abstract void setupQuestionsEasy();
    protected abstract void setupQuestionsMedium();
    protected abstract void setupQuestionsHard();

    //Default no-arg Constructor
    protected Question(){
        //Default difficulty is easy
        this.questionDifficulty = 0;
        this.category = 0;
    }

    //Constructor
    protected Question(int questionDifficulty, int category){
        this.questionDifficulty = questionDifficulty;
        this.category = category;
    }

    protected void createQuestion(int difficulty, int questionIndex, int goodAnswer, String question,
                                String answerA, String answerB, String answerC, String answerD){
        switch(difficulty){
            //Easy
            case 0: {
                this.getQuestionsEasy()[questionIndex] = question;
                this.getAnswersEasy()[questionIndex][0] = answerA;
                this.getAnswersEasy()[questionIndex][1] = answerB;
                this.getAnswersEasy()[questionIndex][2] = answerC;
                this.getAnswersEasy()[questionIndex][3] = answerD;
                this.getGoodAnswerEasy()[questionIndex] = goodAnswer;
            }break;
            //Medium
            case 1: {
                this.getQuestionsMedium()[questionIndex] = question;
                this.getAnswersMedium()[questionIndex][0] = answerA;
                this.getAnswersMedium()[questionIndex][1] = answerB;
                this.getAnswersMedium()[questionIndex][2] = answerC;
                this.getAnswersMedium()[questionIndex][3] = answerD;
                this.getGoodAnswerMedium()[questionIndex] = goodAnswer;
            }break;
            //Hard
            case 2: {
                this.getQuestionsHard()[questionIndex] = question;
                this.getAnswersHard()[questionIndex][0] = answerA;
                this.getAnswersHard()[questionIndex][1] = answerB;
                this.getAnswersHard()[questionIndex][2] = answerC;
                this.getAnswersHard()[questionIndex][3] = answerD;
                this.getGoodAnswerHard()[questionIndex] = goodAnswer;
            }break;
        }
    }

    public String obtainQuestionHard(int indexQuestion){
        return questionsHard[indexQuestion];
    }

    public String obtainQuestionMedium(int indexQuestion){
        return questionsMedium[indexQuestion];
    }

    public String obtainQuestionEasy(int indexQuestion){
        return questionsEasy[indexQuestion];
    }

    public String obtainAnswersEasy(int questionIndex, int answerIndex){
        return answersEasy[questionIndex][answerIndex];
    }

    public String obtainAnswersMedium(int questionIndex, int answerIndex){
        return answersMedium[questionIndex][answerIndex];
    }

    public String obtainAnswersHard(int questionIndex, int answerIndex){
        return answersHard[questionIndex][answerIndex];
    }

    public int getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(int questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public String[] getQuestionsEasy() {
        return questionsEasy;
    }

    public void setQuestionsEasy(String[] questionsEasy) {
        this.questionsEasy = questionsEasy;
    }

    public boolean[] getQuestionsAskedEasy() {
        return questionsAskedEasy;
    }

    public void setQuestionsAskedEasy(boolean[] questionsAskedEasy) {
        this.questionsAskedEasy = questionsAskedEasy;
    }

    public String[] getQuestionsMedium() {
        return questionsMedium;
    }

    public void setQuestionsMedium(String[] questionsMedium) {
        this.questionsMedium = questionsMedium;
    }

    public String[] getQuestionsHard() {
        return questionsHard;
    }

    public void setQuestionsHard(String[] questionsHard) {
        this.questionsHard = questionsHard;
    }

    public boolean[] getQuestionsAskedMedium() {
        return questionsAskedMedium;
    }

    public void setQuestionsAskedMedium(boolean[] questionsAskedMedium) {
        this.questionsAskedMedium = questionsAskedMedium;
    }

    public boolean[] getQuestionsAskedHard() {
        return questionsAskedHard;
    }

    public void setQuestionsAskedHard(boolean[] questionsAskedHard) {
        this.questionsAskedHard = questionsAskedHard;
    }

    public String[][] getAnswersEasy() {
        return answersEasy;
    }

    public void setAnswersEasy(String[][] answersEasy) {
        this.answersEasy = answersEasy;
    }

    public String[][] getAnswersMedium() {
        return answersMedium;
    }

    public void setAnswersMedium(String[][] answersMedium) {
        this.answersMedium = answersMedium;
    }

    public String[][] getAnswersHard() {
        return answersHard;
    }

    public void setAnswersHard(String[][] answersHard) {
        this.answersHard = answersHard;
    }

    public int[] getGoodAnswerEasy() {
        return goodAnswerEasy;
    }

    public void setGoodAnswerEasy(int[] goodAnswerEasy) {
        this.goodAnswerEasy = goodAnswerEasy;
    }

    public int[] getGoodAnswerMedium() {
        return goodAnswerMedium;
    }

    public void setGoodAnswerMedium(int[] goodAnswerMedium) {
        this.goodAnswerMedium = goodAnswerMedium;
    }

    public int[] getGoodAnswerHard() {
        return goodAnswerHard;
    }

    public void setGoodAnswerHard(int[] goodAnswerHard) {
        this.goodAnswerHard = goodAnswerHard;
    }

    public int getNumOfCategories() {
        return numOfCategories;
    }

    public void setNumOfCategories(int numOfCategories) {
        this.numOfCategories = numOfCategories;
    }
}
