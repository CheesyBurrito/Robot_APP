package com.example.william.robot_app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String clientIPAddress = "192.168.0.1";
    final Handler handler = new Handler();
    private int currentQuestionIndex = 0;
    private int currentIndexQuestionCategory = 0;
    private TextView questionText;
    private Button playButton;
    private Button difficultySelectionEasy;
    private Button difficultySelectionMedium;
    private Button difficultySelectionHard;
    private Button buttonStopReceiving;
    private TextView textViewDataFromClient;
    private Button question1;
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private HistoryQuestion historyQuestion;
    private SportQuestion sportQuestion;
    private GeographyQuestion geographyQuestion;
    private PopCultureQuestion popCultureQuestion;
    private ScienceQuestion scienceQuestion;
    private boolean end = false;
    Server server;
    TextView infoip, msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startServerSocket();
        //Initializing all UI elements

        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);
        server = new Server(this);
        //infoip.setText(server.getIpAddress() + ":" + server.getPort());
        playButton = (Button) findViewById(R.id.playButton);
        questionText = (TextView) findViewById(R.id.questionText);
        answerA = (Button) findViewById(R.id.answerA);
        answerB = (Button) findViewById(R.id.answerB);
        answerC = (Button) findViewById(R.id.answerC);
        answerD = (Button) findViewById(R.id.answerD);
        difficultySelectionEasy = (Button) findViewById(R.id.difficultySelectionEasy);
        difficultySelectionMedium = (Button) findViewById(R.id.difficultySelectionMedium);
        difficultySelectionHard = (Button) findViewById(R.id.difficultySelectionHard);

        //Starting the UI
        removeUIDifficultySelection();
        removeUIGameMode();
        updateUIPlayMode();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.onDestroy();
    }


    public void startGame(){
        //Contains the logic for the game
        askQuestion(currentIndexQuestionCategory);

        //How to wait for next turn?
        //Need to implement codes for colors, questions status,



        /**
         * General Logic for the game loop
         * 1- robot reads current color
         * 2- robot sends current color to application
         * 3- application processes what was sent by client
         * 4- robot randomizes a number between 0 - 12 (inclusive)
         * 5- robot sends the randomized number to application
         * 6- application processes the information sent by the client
         * 7- application verifies that question was not previously asked
         * 7.1- if already asked, application sends an error code to robot
         * 7.2- robot sends a new randomized number
         * 8- application shows the question with the multiple answers, with timer
         * 8.1- if timer runs out, return to robot question failed
         * 9- return to robot the selected answer and the right answer
         * 10- robot checks the question's status
         * 11- robot sends to server if question was correct or incorrect
         * 12- server processes information
         * 12.1- if question successed, remove from index of questions
         * 13- robot decides what to do with result of question
         * 14- waits for other robot's turn to end
         *
         */
    }

    public void removeUIDifficultySelection(){
        difficultySelectionEasy.setActivated(false);
        difficultySelectionMedium.setActivated(false);
        difficultySelectionHard.setActivated(false);
        difficultySelectionEasy.setVisibility(View.GONE);
        difficultySelectionMedium.setVisibility(View.GONE);
        difficultySelectionHard.setVisibility(View.GONE);
        questionText.setVisibility(View.GONE);
    }

    public void updateUIDifficultySelection(){
        questionText.setVisibility(View.VISIBLE);
        questionText.setText("Choose a difficulty");
        difficultySelectionEasy.setActivated(true);
        difficultySelectionMedium.setActivated(true);
        difficultySelectionHard.setActivated(true);
        difficultySelectionEasy.setVisibility(View.VISIBLE);
        difficultySelectionMedium.setVisibility(View.VISIBLE);
        difficultySelectionHard.setVisibility(View.VISIBLE);
    }

    public void removeUIGameMode(){
        questionText.setActivated(false);
        answerA.setActivated(false);
        answerB.setActivated(false);
        answerC.setActivated(false);
        answerD.setActivated(false);

        answerA.setVisibility(View.GONE);
        answerB.setVisibility(View.GONE);
        answerC.setVisibility(View.GONE);
        answerD.setVisibility(View.GONE);
        questionText.setVisibility(View.GONE);
    }

    public void removeUIPlayMode(){
        playButton.setActivated(false);
        playButton.setVisibility(View.INVISIBLE);
    }

    public void updateUIGameMode(){
        questionText.setActivated(true);
        answerA.setActivated(true);
        answerB.setActivated(true);
        answerC.setActivated(true);
        answerD.setActivated(true);

        answerA.setVisibility(View.VISIBLE);
        answerB.setVisibility(View.VISIBLE);
        answerC.setVisibility(View.VISIBLE);
        answerD.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
    }

    public void updateUIPlayMode(){
        playButton.setActivated(true);
        playButton.setVisibility(View.VISIBLE);
    }

    protected void askQuestionHistory(){
        switch(historyQuestion.getQuestionDifficulty()){
            case 0: {
                questionText.setText(historyQuestion.obtainQuestionEasy(currentQuestionIndex));
                answerA.setText(historyQuestion.obtainAnswersEasy(currentQuestionIndex, 0));
                answerB.setText(historyQuestion.obtainAnswersEasy(currentQuestionIndex, 1));
                answerC.setText(historyQuestion.obtainAnswersEasy(currentQuestionIndex, 2));
                answerD.setText(historyQuestion.obtainAnswersEasy(currentQuestionIndex, 3));
            }break;

            case 1: {
                questionText.setText(historyQuestion.obtainQuestionMedium(currentQuestionIndex));
                answerA.setText(historyQuestion.obtainAnswersMedium(currentQuestionIndex, 0));
                answerB.setText(historyQuestion.obtainAnswersMedium(currentQuestionIndex, 1));
                answerC.setText(historyQuestion.obtainAnswersMedium(currentQuestionIndex, 2));
                answerD.setText(historyQuestion.obtainAnswersMedium(currentQuestionIndex, 3));
            }break;

            case 2: {
                questionText.setText(historyQuestion.obtainQuestionHard(currentQuestionIndex));
                answerA.setText(historyQuestion.obtainAnswersHard(currentQuestionIndex, 0));
                answerB.setText(historyQuestion.obtainAnswersHard(currentQuestionIndex, 1));
                answerC.setText(historyQuestion.obtainAnswersHard(currentQuestionIndex, 2));
                answerD.setText(historyQuestion.obtainAnswersHard(currentQuestionIndex, 3));
            }break;
        }
    }

    protected void askQuestionSport(){
        switch(sportQuestion.getQuestionDifficulty()){
            case 0: {
                questionText.setText(sportQuestion.obtainQuestionEasy(currentQuestionIndex));
                answerA.setText(sportQuestion.obtainAnswersEasy(currentQuestionIndex, 0));
                answerB.setText(sportQuestion.obtainAnswersEasy(currentQuestionIndex, 1));
                answerC.setText(sportQuestion.obtainAnswersEasy(currentQuestionIndex, 2));
                answerD.setText(sportQuestion.obtainAnswersEasy(currentQuestionIndex, 3));
            }break;

            case 1: {
                questionText.setText(sportQuestion.obtainQuestionMedium(currentQuestionIndex));
                answerA.setText(sportQuestion.obtainAnswersMedium(currentQuestionIndex, 0));
                answerB.setText(sportQuestion.obtainAnswersMedium(currentQuestionIndex, 1));
                answerC.setText(sportQuestion.obtainAnswersMedium(currentQuestionIndex, 2));
                answerD.setText(sportQuestion.obtainAnswersMedium(currentQuestionIndex, 3));
            }break;

            case 2: {
                questionText.setText(sportQuestion.obtainQuestionHard(currentQuestionIndex));
                answerA.setText(sportQuestion.obtainAnswersHard(currentQuestionIndex, 0));
                answerB.setText(sportQuestion.obtainAnswersHard(currentQuestionIndex, 1));
                answerC.setText(sportQuestion.obtainAnswersHard(currentQuestionIndex, 2));
                answerD.setText(sportQuestion.obtainAnswersHard(currentQuestionIndex, 3));
            }break;
        }
    }

    protected void askQuestionScience(){
        switch(scienceQuestion.getQuestionDifficulty()){
            case 0: {
                questionText.setText(scienceQuestion.obtainQuestionEasy(currentQuestionIndex));
                answerA.setText(scienceQuestion.obtainAnswersEasy(currentQuestionIndex, 0));
                answerB.setText(scienceQuestion.obtainAnswersEasy(currentQuestionIndex, 1));
                answerC.setText(scienceQuestion.obtainAnswersEasy(currentQuestionIndex, 2));
                answerD.setText(scienceQuestion.obtainAnswersEasy(currentQuestionIndex, 3));
            }break;

            case 1: {
                questionText.setText(scienceQuestion.obtainQuestionMedium(currentQuestionIndex));
                answerA.setText(scienceQuestion.obtainAnswersMedium(currentQuestionIndex, 0));
                answerB.setText(scienceQuestion.obtainAnswersMedium(currentQuestionIndex, 1));
                answerC.setText(scienceQuestion.obtainAnswersMedium(currentQuestionIndex, 2));
                answerD.setText(scienceQuestion.obtainAnswersMedium(currentQuestionIndex, 3));
            }break;

            case 2: {
                questionText.setText(scienceQuestion.obtainQuestionHard(currentQuestionIndex));
                answerA.setText(scienceQuestion.obtainAnswersHard(currentQuestionIndex, 0));
                answerB.setText(scienceQuestion.obtainAnswersHard(currentQuestionIndex, 1));
                answerC.setText(scienceQuestion.obtainAnswersHard(currentQuestionIndex, 2));
                answerD.setText(scienceQuestion.obtainAnswersHard(currentQuestionIndex, 3));
            }break;
        }
    }

    protected void askQuestionGeography(){
        switch(geographyQuestion.getQuestionDifficulty()){
            case 0: {
                questionText.setText(geographyQuestion.obtainQuestionEasy(currentQuestionIndex));
                answerA.setText(geographyQuestion.obtainAnswersEasy(currentQuestionIndex, 0));
                answerB.setText(geographyQuestion.obtainAnswersEasy(currentQuestionIndex, 1));
                answerC.setText(geographyQuestion.obtainAnswersEasy(currentQuestionIndex, 2));
                answerD.setText(geographyQuestion.obtainAnswersEasy(currentQuestionIndex, 3));
            }break;

            case 1: {
                questionText.setText(geographyQuestion.obtainQuestionMedium(currentQuestionIndex));
                answerA.setText(geographyQuestion.obtainAnswersMedium(currentQuestionIndex, 0));
                answerB.setText(geographyQuestion.obtainAnswersMedium(currentQuestionIndex, 1));
                answerC.setText(geographyQuestion.obtainAnswersMedium(currentQuestionIndex, 2));
                answerD.setText(geographyQuestion.obtainAnswersMedium(currentQuestionIndex, 3));
            }break;

            case 2: {
                questionText.setText(geographyQuestion.obtainQuestionHard(currentQuestionIndex));
                answerA.setText(geographyQuestion.obtainAnswersHard(currentQuestionIndex, 0));
                answerB.setText(geographyQuestion.obtainAnswersHard(currentQuestionIndex, 1));
                answerC.setText(geographyQuestion.obtainAnswersHard(currentQuestionIndex, 2));
                answerD.setText(geographyQuestion.obtainAnswersHard(currentQuestionIndex, 3));
            }break;
        }
    }

    protected void askQuestionPopCulture(){
        switch(popCultureQuestion.getQuestionDifficulty()){
            case 0: {
                questionText.setText(popCultureQuestion.obtainQuestionEasy(currentQuestionIndex));
                answerA.setText(popCultureQuestion.obtainAnswersEasy(currentQuestionIndex, 0));
                answerB.setText(popCultureQuestion.obtainAnswersEasy(currentQuestionIndex, 1));
                answerC.setText(popCultureQuestion.obtainAnswersEasy(currentQuestionIndex, 2));
                answerD.setText(popCultureQuestion.obtainAnswersEasy(currentQuestionIndex, 3));
            }break;

            case 1: {
                questionText.setText(popCultureQuestion.obtainQuestionMedium(currentQuestionIndex));
                answerA.setText(popCultureQuestion.obtainAnswersMedium(currentQuestionIndex, 0));
                answerB.setText(popCultureQuestion.obtainAnswersMedium(currentQuestionIndex, 1));
                answerC.setText(popCultureQuestion.obtainAnswersMedium(currentQuestionIndex, 2));
                answerD.setText(popCultureQuestion.obtainAnswersMedium(currentQuestionIndex, 3));
            }break;

            case 2: {
                questionText.setText(popCultureQuestion.obtainQuestionHard(currentQuestionIndex));
                answerA.setText(popCultureQuestion.obtainAnswersHard(currentQuestionIndex, 0));
                answerB.setText(popCultureQuestion.obtainAnswersHard(currentQuestionIndex, 1));
                answerC.setText(popCultureQuestion.obtainAnswersHard(currentQuestionIndex, 2));
                answerD.setText(popCultureQuestion.obtainAnswersHard(currentQuestionIndex, 3));
            }break;
        }
    }

    protected void askQuestionRandom(){
        //Generates a number between 0 - 5 and that is the question category to be asked

    }

    protected void askQuestion(int questionCategoryIndex){
        switch(questionCategoryIndex){
            case 0: {
                askQuestionHistory();
            }break;

            case 1: {
                askQuestionSport();
            }break;

            case 2: {
                askQuestionScience();
            }break;

            case 3: {
                askQuestionGeography();
            }break;

            case 4: {
                askQuestionPopCulture();
            }break;

            case 5: {
                askQuestionRandom();
            }break;
        }

    }


    public void verifyAnswers(int indexButtonClicked, int questionCategoryIndex){
        switch(questionCategoryIndex){
            case 0: {
                verifyAnswersHistory(indexButtonClicked);
            }break;

            case 1: {
                verifyAnswersSport(indexButtonClicked);
            }break;

            case 2: {
                verifyAnswersScience(indexButtonClicked);
            }break;

            case 3: {
                verifyAnswersGeography(indexButtonClicked);
            }break;

            case 4: {
                verifyAnswersPopCulture(indexButtonClicked);
            }break;

            case 5: {
                verifyAnswersRandom(indexButtonClicked);
            }break;
        }
    }

    public void verifyAnswersHistory(int indexButtonClicked){
        switch(historyQuestion.getQuestionDifficulty()){
            case 0: {
                if(historyQuestion.getGoodAnswerEasy()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }
            }break;

            case 1: {
                if(historyQuestion.getGoodAnswerMedium()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;

            case 2: {
                if(historyQuestion.getGoodAnswerHard()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;
        }
    }

    public void verifyAnswersSport(int indexButtonClicked){
        switch(sportQuestion.getQuestionDifficulty()){
            case 0: {
                if(sportQuestion.getGoodAnswerEasy()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }
            }break;

            case 1: {
                if(sportQuestion.getGoodAnswerMedium()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;

            case 2: {
                if(sportQuestion.getGoodAnswerHard()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;
        }
    }

    public void verifyAnswersScience(int indexButtonClicked){
        switch(scienceQuestion.getQuestionDifficulty()){
            case 0: {
                if(scienceQuestion.getGoodAnswerEasy()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }
            }break;

            case 1: {
                if(scienceQuestion.getGoodAnswerMedium()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;

            case 2: {
                if(scienceQuestion.getGoodAnswerHard()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;
        }
    }

    public void verifyAnswersGeography(int indexButtonClicked){
        switch(geographyQuestion.getQuestionDifficulty()){
            case 0: {
                if(geographyQuestion.getGoodAnswerEasy()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }
            }break;

            case 1: {
                if(geographyQuestion.getGoodAnswerMedium()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;

            case 2: {
                if(geographyQuestion.getGoodAnswerHard()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;
        }
    }

    public void verifyAnswersPopCulture(int indexButtonClicked){
        switch(popCultureQuestion.getQuestionDifficulty()){
            case 0: {
                if(popCultureQuestion.getGoodAnswerEasy()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }
            }break;

            case 1: {
                if(popCultureQuestion.getGoodAnswerMedium()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;

            case 2: {
                if(popCultureQuestion.getGoodAnswerHard()[currentQuestionIndex] == indexButtonClicked){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;
        }
    }

    //Will need to be correctly implemented
    public void verifyAnswersRandom(int indexButtonClicked){
        if(sportQuestion.getGoodAnswerEasy()[currentQuestionIndex] == indexButtonClicked){
            questionText.setText("GOOD!");
        }
        else{
            questionText.setText("BAD!");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.difficultySelectionEasy: {
                historyQuestion = new HistoryQuestion(0);
                sportQuestion = new SportQuestion(0);
                geographyQuestion = new GeographyQuestion(0);
                popCultureQuestion = new PopCultureQuestion(0);
                scienceQuestion = new ScienceQuestion(0);
                removeUIDifficultySelection();
                updateUIGameMode();
                startGame();
            }break;

            case R.id.difficultySelectionMedium: {
                historyQuestion = new HistoryQuestion(1);
                sportQuestion = new SportQuestion(1);
                geographyQuestion = new GeographyQuestion(1);
                popCultureQuestion = new PopCultureQuestion(1);
                scienceQuestion = new ScienceQuestion(1);
                removeUIDifficultySelection();
                updateUIGameMode();
                startGame();
            }break;

            case R.id.difficultySelectionHard: {
                historyQuestion = new HistoryQuestion(2);
                sportQuestion = new SportQuestion(2);
                geographyQuestion = new GeographyQuestion(2);
                popCultureQuestion = new PopCultureQuestion(2);
                scienceQuestion = new ScienceQuestion(2);
                removeUIDifficultySelection();
                updateUIGameMode();
                startGame();
            }break;


            case R.id.playButton: {
                removeUIPlayMode();
                updateUIDifficultySelection();
            }break;

            case R.id.answerA: {
                verifyAnswers(0, currentIndexQuestionCategory);
            }break;

            case R.id.answerB: {
                verifyAnswers(1, currentIndexQuestionCategory);

            }break;

            case R.id.answerC: {
                verifyAnswers(2, currentIndexQuestionCategory);

            }break;

            case R.id.answerD: {
                verifyAnswers(3, currentIndexQuestionCategory);
            }break;
        }

    }

}

