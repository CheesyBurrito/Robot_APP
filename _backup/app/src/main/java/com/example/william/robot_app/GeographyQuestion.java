package com.example.william.robot_app;

/**
 * Created by William on 11/9/2017.
 */

public class GeographyQuestion extends Question {

    //Default no-arg Constructor
    public GeographyQuestion(){
        super();
        setupQuestions();
    }

    //Constructor with args
    public GeographyQuestion(int questionDifficulty){
        super(questionDifficulty, 3);
        setupQuestions();
    }

    @Override
    protected void setupQuestions() {
        //Setup the questions for the category
        setupQuestionsEasy();
        setupQuestionsMedium();
        setupQuestionsHard();
    }

    @Override
    protected void setupQuestionsEasy() {
        //Question 1
        this.createQuestion(0, 0, 1, "En quelle annee a ete fonde Canada?", "1967",
                "1867", "2006", "1608");

        //Question 2

        //Question 3

        //Question 4

        //Question 5

        //Question 6

        //Question 7

        //Question 8

        //Question 9

        //Question 10

        //Question 11

        //Question 12
    }

    @Override
    protected void setupQuestionsMedium() {
        //Question 1
        this.createQuestion(1, 0, 0, "APP", "Lol",
                "Rofl", "Answer", "good Answer");

        //Question 2

        //Question 3

        //Question 4

        //Question 5

        //Question 6

        //Question 7

        //Question 8

        //Question 9

        //Question 10

        //Question 11

        //Question 12

    }

    @Override
    protected void setupQuestionsHard() {
        //Question 1
        this.createQuestion(2, 0, 0, "BOB", "Lol",
                "Rofl", "Answer", "good Answer");

        //Question 2

        //Question 3

        //Question 4

        //Question 5

        //Question 6

        //Question 7

        //Question 8

        //Question 9

        //Question 10

        //Question 11

        //Question 12

    }
}
