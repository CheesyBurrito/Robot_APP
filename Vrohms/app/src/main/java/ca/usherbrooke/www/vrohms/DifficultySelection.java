package ca.usherbrooke.www.vrohms;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DifficultySelection extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection);
    }

    public void choseDifficulty(View view)
    {
        int difficulty = -1;
        String tag = view.getTag().toString();

        switch (tag)
        {
            case "easyDifficulty": { difficulty = 0; break; }
            case "mediumDifficulty": { difficulty = 1; break; }
            case "hardDifficulty": { difficulty = 2; break; }
            default: {  }
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra("difficulty", difficulty);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
