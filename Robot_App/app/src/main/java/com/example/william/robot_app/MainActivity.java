package com.example.william.robot_app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean questionCompleted = false;
    double timer;
    CountDownTimer countDownTimer;
    final Handler handler = new Handler();
    private int currentQuestionIndex = 0;
    private int currentIndexQuestionCategory = 0;
    TextView questionText;
    String currentStringSend = "";
    private Button playButton;
    private Button difficultySelectionEasy;
    private Button difficultySelectionMedium;
    private Button difficultySelectionHard;
    private Button buttonStopReceiving;
    private TextView textViewDataFromClient;
    private TextView countdownTimer;
    private Button question1;
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing all UI elements
        countdownTimer = (TextView) findViewById(R.id.countdownTimer);
        server = new Server(this);
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

    public void startGame(int difficulty){
        //Contains the logic for the game
        //removeUIGameMode();
        //Setting the array containing all of the question objects
        /*
        categoryQuestionArray[0] = historyQuestion;
        categoryQuestionArray[1] = sportQuestion;
        categoryQuestionArray[2] = scienceQuestion;
        categoryQuestionArray[3] = geographyQuestion;
        categoryQuestionArray[4] = popCultureQuestion;
        */
        //askQuestion(currentIndexQuestionCategory);
        timer = System.currentTimeMillis();
        sendDifficulty(difficulty);
        //questionCompleted = false;


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
         * 14- waits for other robot's turn to end???????????????
         *
         */
    }

    public void startTimer(){
       countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                countdownTimer.setText("Temps restant: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countdownTimer.setText("Terminé!");
                verifyAnswers(4);
            }
        }.start();
    }

    public String handleReceivedData(String dataReceived){
        if(dataReceived.length() == 0){
            return "String of length 0";
        }

        questionCompleted = false;

        switch(dataReceived.charAt(0)){
            case '0': {
                //String for question
                removeUIGameMode();
                questionText.setText(dataReceived.substring(1));
            }break;

            case '1': {
                //Answer A
                answerA.setText(dataReceived.substring(1));

            }break;

            case '2': {
                //Answer B
                answerB.setText(dataReceived.substring(1));
            }break;

            case '3': {
                //Answer C
                answerC.setText(dataReceived.substring(1));

            }break;

            case '4': {
                //Answer D
                answerD.setText(dataReceived.substring(1));

            }break;

            case '5': {
                //Question success
                updateUIAnswerSent("Bonne Réponse");

            }break;

            case '6': {
                //Question failed
                updateUIAnswerSent("Mauvaise Réponse");

            }break;

            case '7': {
                //Game Completed
                timer = System.currentTimeMillis() - timer;
                int minute = (int) timer / 1000;
                minute = minute / 60;
                int seconde = (int) timer / 1000;
                seconde = seconde % 60;
                String timerText = "" + minute + " minutes " + seconde + " secondes";
                updateUIAnswerSent(timerText);

            }break;

            case '8': {
                //Load completed
                //Show GameUI
                updateUIGameMode();
                startTimer();
            }break;
        }
        return "No Valid Code!";

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
        questionText.setText("Choisissez votre diificulté");
        difficultySelectionEasy.setActivated(true);
        difficultySelectionMedium.setActivated(true);
        difficultySelectionHard.setActivated(true);
        difficultySelectionEasy.setVisibility(View.VISIBLE);
        difficultySelectionMedium.setVisibility(View.VISIBLE);
        difficultySelectionHard.setVisibility(View.VISIBLE);
    }

    public void removeUIGameMode(){
        countdownTimer.setActivated(false);
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
        countdownTimer.setVisibility(View.GONE);
    }

    public void removeUIPlayMode(){
        //playButton.setActivated(false);
        playButton.setVisibility(View.INVISIBLE);
    }

    public void updateUIGameMode(){
        countdownTimer.setActivated(true);
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
        countdownTimer.setVisibility(View.VISIBLE);
    }

    public void updateUIPlayMode(){
        playButton.setActivated(true);
        playButton.setVisibility(View.VISIBLE);
    }

    public void updateUIAnswerSent(String text){
        questionText.setActivated(true);
        questionText.setText(text);
        questionText.setVisibility(View.VISIBLE);
    }

    public void sendDifficulty(int difficulty){
        currentStringSend = "";
        switch(difficulty){
            case 0: currentStringSend = "0";break;
            case 1: currentStringSend = "1";break;
            case 2: currentStringSend = "2";break;
        }
        questionCompleted = true;
    }

    //Change so that the check is through the robot
    public void verifyAnswers(int indexButtonClicked){
        countDownTimer.cancel();

        switch(indexButtonClicked){
            case 0: currentStringSend = "A";break;
            case 1: currentStringSend = "B";break;
            case 2: currentStringSend = "C";break;
            case 3: currentStringSend = "D";break;
            case 4: currentStringSend = "E";break;
        }
        questionCompleted = true;
        removeUIGameMode();
        updateUIAnswerSent("Réponse Envoyée");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.difficultySelectionEasy: {
                /*
                historyQuestion = new HistoryQuestion(0);
                sportQuestion = new SportQuestion(0);
                geographyQuestion = new GeographyQuestion(0);
                popCultureQuestion = new PopCultureQuestion(0);
                scienceQuestion = new ScienceQuestion(0);
                */
                removeUIDifficultySelection();
                updateUIGameMode();
                startGame(0);
            }break;

            case R.id.difficultySelectionMedium: {
                /*
                historyQuestion = new HistoryQuestion(1);
                sportQuestion = new SportQuestion(1);
                geographyQuestion = new GeographyQuestion(1);
                popCultureQuestion = new PopCultureQuestion(1);
                scienceQuestion = new ScienceQuestion(1);
                */
                removeUIDifficultySelection();
                updateUIGameMode();
                startGame(1);
            }break;

            case R.id.difficultySelectionHard: {
                /*
                historyQuestion = new HistoryQuestion(2);
                sportQuestion = new SportQuestion(2);
                geographyQuestion = new GeographyQuestion(2);
                popCultureQuestion = new PopCultureQuestion(2);
                scienceQuestion = new ScienceQuestion(2);
                */
                removeUIDifficultySelection();
                updateUIGameMode();
                startGame(2);
            }break;


            case R.id.playButton: {
                removeUIPlayMode();
                updateUIDifficultySelection();
            }break;

            case R.id.answerA: {
                verifyAnswers(0);
            }break;

            case R.id.answerB: {
                verifyAnswers(1);

            }break;

            case R.id.answerC: {
                verifyAnswers(2);

            }break;

            case R.id.answerD: {
                verifyAnswers(3);
            }break;
        }

    }

}