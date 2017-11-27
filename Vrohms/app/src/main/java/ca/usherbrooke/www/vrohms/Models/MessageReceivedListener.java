package ca.usherbrooke.www.vrohms.Models;

/**
 * Created by hugo on 2017-11-26.
 */

public interface MessageReceivedListener
{
    void onQuestionReceived(Question question);
    void onQuestionSuccess(Boolean isCorrect);
    void onGameSessionEnd();
}