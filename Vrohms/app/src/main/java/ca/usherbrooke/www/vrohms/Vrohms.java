package ca.usherbrooke.www.vrohms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ca.usherbrooke.www.vrohms.Data.RobotDao;
import ca.usherbrooke.www.vrohms.Models.MessageReceivedListener;
import ca.usherbrooke.www.vrohms.Models.Question;

public class Vrohms extends AppCompatActivity
{
    public static final int DIFFICULTY_SELECTION_ACTIVITY = 1;
    public static final int GAME_ACTIVITY = 2;

    private RobotDao robotDao;
    private double gameStartTimestamp;

    protected TextView centerLabel;
    protected ImageButton playButton;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrohms);

        // initializing server
        robotDao = RobotDao.getInstance();

        centerLabel = (TextView) findViewById(R.id.centerLabel);
        playButton = (ImageButton) findViewById(R.id.playButton);
    }

    public void displayDifficultySelectionActivity(View view)
    {
        Intent difficultySelectionIntent = new Intent(this, DifficultySelection.class);
        startActivityForResult(difficultySelectionIntent, DIFFICULTY_SELECTION_ACTIVITY);
    }

    private void displayGameActivity()
    {
        Intent gameIntent = new Intent(this, Game.class);
        startActivityForResult(gameIntent, GAME_ACTIVITY);
    }

    public void onActivityResult(int activity, int result, Intent data)
    {
        if (activity == DIFFICULTY_SELECTION_ACTIVITY && result == RESULT_OK)
        {
            centerLabel.setText("Connection au robot!");
            playButton.setVisibility(View.GONE);
            
            int difficulty = data.getIntExtra("difficulty", -1);
            robotDao.sendDifficulty(difficulty);

            // game officialy start
            gameStartTimestamp = System.currentTimeMillis();
            displayGameActivity();
        }
        else if (activity == GAME_ACTIVITY && result == RESULT_OK)
        {
            // temporary code
            double totalGameTime = System.currentTimeMillis() - gameStartTimestamp;
            int minute = (int) totalGameTime / 1000;
            minute = minute / 60;
            int seconde = (int) totalGameTime / 1000;
            seconde = seconde % 60;
            String timerText = minute + " minutes " + seconde + " secondes";
            centerLabel.setText(timerText);

            // game has ended
            // display victory screen with time
            // reset display and server
            // player should be able to press the play button again
        }
    }
}