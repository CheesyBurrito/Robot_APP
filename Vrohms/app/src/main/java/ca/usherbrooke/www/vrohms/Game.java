package ca.usherbrooke.www.vrohms;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ca.usherbrooke.www.vrohms.Data.RobotDao;
import ca.usherbrooke.www.vrohms.Models.MessageReceivedListener;
import ca.usherbrooke.www.vrohms.Models.Question;

public class Game extends AppCompatActivity implements MessageReceivedListener
{
    public static final int QUESTION_TIMER_DELAY = 120000;

    private RobotDao robotDao;
    private CountDownTimer questionTimer;

    protected TextView questionTextLabel;
    protected ProgressBar progressBar;
    protected GridLayout answersGridLayout;
    protected Button answerAAction;
    protected Button answerBAction;
    protected Button answerCAction;
    protected Button answerDAction;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questionTextLabel = (TextView) findViewById(R.id.questionTextLabel);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        answersGridLayout = (GridLayout) findViewById(R.id.answersGridLayout);
        answerAAction = (Button) findViewById(R.id.answerAAction);
        answerBAction = (Button) findViewById(R.id.answerBAction);
        answerCAction = (Button) findViewById(R.id.answerCAction);
        answerDAction = (Button) findViewById(R.id.answerDAction);

        robotDao = RobotDao.getInstance();
        robotDao.addListener(this);

        questionTextLabel.setText("Connexion au robot !");
        answersGridLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        robotDao.removeListener(this);

        if (questionTimer != null)
            questionTimer.cancel();
    }



    @Override
    public void onQuestionReceived(Question question)
    {
        answersGridLayout.setVisibility(View.VISIBLE);

        questionTextLabel.setText(question.getStatement());
        answerAAction.setText(question.getAnswerA());
        answerBAction.setText(question.getAnswerB());
        answerCAction.setText(question.getAnswerC());
        answerDAction.setText(question.getAnswerD());

        startTimer();
    }

    @Override
    public void onQuestionSuccess(Boolean isCorrect)
    {
        if (isCorrect)
            questionTextLabel.setText("Bonne réponse !");
        else
            questionTextLabel.setText("Mauvaise réponse :(");
    }

    @Override
    public void onGameSessionEnd()
    {
        // return to main screen
        finish();
    }



    public void chooseAnswer(View view)
    {
        String answer = view.getTag().toString();
        robotDao.sendAnswer(answer);

        questionTimer.cancel();
        answersGridLayout.setVisibility(View.GONE);
        //questionTextLabel.setText("Réponse envoyée  !");
    }

    private void startTimer()
    {
        progressBar.setProgress(0);

        questionTimer = new CountDownTimer(QUESTION_TIMER_DELAY, 250)
        {
            public void onTick(long millisUntilFinished)
            {
                //countdownTimer.setText("Temps restant: " + millisUntilFinished / 1000);
                int percentageLeft = (int) (millisUntilFinished / QUESTION_TIMER_DELAY * 100);
                progressBar.setProgress(percentageLeft);
            }

            public void onFinish()
            {
                robotDao.sendAnswer("E");
            }

        }.start();
    }

}
