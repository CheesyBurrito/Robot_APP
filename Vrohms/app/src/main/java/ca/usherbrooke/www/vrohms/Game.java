package ca.usherbrooke.www.vrohms;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private RobotDao robotDao;

    protected TextView questionTextLabel;
    protected GridLayout answersGridLayout;
    protected Button answerAAction;
    protected Button answerBAction;
    protected Button answerCAction;
    protected Button answerDAction;

    private Question currentQuestion;
    private Boolean isCorrect;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questionTextLabel = (TextView) findViewById(R.id.questionTextLabel);
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
    }



    @Override
    public void onQuestionReceived(Question question)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                questionTextLabel.setTextColor(Color.BLACK);
                answersGridLayout.setVisibility(View.VISIBLE);
                Question question = robotDao.getPendingQuestion();

                questionTextLabel.setText(question.getStatement());
                answerAAction.setText(question.getAnswerA());
                answerBAction.setText(question.getAnswerB());
                answerCAction.setText(question.getAnswerC());
                answerDAction.setText(question.getAnswerD());
            }
        });
    }

    @Override
    public void onQuestionSuccess(Boolean data)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Boolean isCorrect = robotDao.getIsCorrect();

                if (isCorrect)
                {
                    questionTextLabel.setTextColor(0xFF8BC34A);
                    questionTextLabel.setText("Bonne réponse !");
                }
                else
                {
                    questionTextLabel.setTextColor(0xFFFF5722);
                    questionTextLabel.setText("Mauvaise réponse :(");
                }
            }
        });
    }

    @Override
    public void onGameSessionEnd()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                setResult(RESULT_OK);
                finish();
            }
        });
    }



    public void chooseAnswer(View view)
    {
        String answer = view.getTag().toString();
        robotDao.sendAnswer(answer);

        answersGridLayout.setVisibility(View.GONE);
    }

}
