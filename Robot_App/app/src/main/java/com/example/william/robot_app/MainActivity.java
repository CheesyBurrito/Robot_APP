package com.example.william.robot_app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final Handler handler = new Handler();
    private int currentQuestionIndex = 0;
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
    private boolean end = false;

    /*
     * Implement Question answer checking for multiple difficulty
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startServerSocket();

        //historyQuestion = new HistoryQuestion(0);

        playButton = (Button) findViewById(R.id.playButton);

        questionText = (TextView) findViewById(R.id.questionText);
        answerA = (Button) findViewById(R.id.answerA);
        answerB = (Button) findViewById(R.id.answerB);
        answerC = (Button) findViewById(R.id.answerC);
        answerD = (Button) findViewById(R.id.answerD);

        difficultySelectionEasy = (Button) findViewById(R.id.difficultySelectionEasy);
        difficultySelectionMedium = (Button) findViewById(R.id.difficultySelectionMedium);
        difficultySelectionHard = (Button) findViewById(R.id.difficultySelectionHard);
        removeUIDifficultySelection();
        removeUIGameMode();
        updateUIPlayMode();

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

    protected void askQuestion(){

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

    private void startServerSocket() {

        Thread thread = new Thread(new Runnable() {

            private String stringData = null;

            @Override
            public void run() {

                try {

                    ServerSocket ss = new ServerSocket(1234);

                    while (!end) {
                        //Server is waiting for client here, if needed
                        Socket s = ss.accept();
                        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter output = new PrintWriter(s.getOutputStream());

                        stringData = input.readLine();
                        output.println("FROM SERVER - " + stringData.toUpperCase());
                        output.flush();

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        updateUI(stringData);
                        if (stringData.equalsIgnoreCase("STOP")) {
                            end = true;
                            output.close();
                            s.close();
                            break;
                        }

                        output.close();
                        s.close();
                    }
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }


    private void updateUI(final String stringData) {

        handler.post(new Runnable() {
            @Override
            public void run() {

                String s = textViewDataFromClient.getText().toString();
                if (stringData.trim().length() != 0)
                    textViewDataFromClient.setText(s + "\n" + "From Client : " + stringData);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.difficultySelectionEasy: {
                historyQuestion = new HistoryQuestion(0);
                removeUIDifficultySelection();
                updateUIGameMode();
                askQuestion();
            }break;

            case R.id.difficultySelectionMedium: {
                historyQuestion = new HistoryQuestion(1);
                removeUIDifficultySelection();
                updateUIGameMode();
                askQuestion();
            }break;

            case R.id.difficultySelectionHard: {
                historyQuestion = new HistoryQuestion(2);
                removeUIDifficultySelection();
                updateUIGameMode();
                askQuestion();
            }break;

            case R.id.playButton: {
                removeUIPlayMode();
                updateUIDifficultySelection();
                //updateUIGameMode();
                //askQuestion();
            }break;

            case R.id.answerA: {
                if(historyQuestion.getGoodAnswerEasy()[currentQuestionIndex] == 0){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;

            case R.id.answerB: {
                if(historyQuestion.getGoodAnswerEasy()[currentQuestionIndex] == 1){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;

            case R.id.answerC: {
                if(historyQuestion.getGoodAnswerEasy()[currentQuestionIndex] == 2){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;

            case R.id.answerD: {
                if(historyQuestion.getGoodAnswerEasy()[currentQuestionIndex] == 3){
                    questionText.setText("GOOD!");
                }
                else{
                    questionText.setText("BAD!");
                }

            }break;
        }

    }

}

