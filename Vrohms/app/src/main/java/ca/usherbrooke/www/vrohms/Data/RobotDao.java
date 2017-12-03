package ca.usherbrooke.www.vrohms.Data;

import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import ca.usherbrooke.www.vrohms.Models.MessageReceivedListener;
import ca.usherbrooke.www.vrohms.Models.Question;

/**
 * Created by hugo on 2017-11-26.
 */

public class RobotDao
{
    private static RobotDao instance;
    private Server server;

    //private Boolean dataLock = false;
    private List<MessageReceivedListener> listeners = new ArrayList<MessageReceivedListener>();
    private Question pendingQuestion = null;
    private Boolean isCorrect;



    public static RobotDao getInstance()
    {
        if (instance == null)
        {
            instance = new RobotDao();
        }
        return instance;
    }

    private RobotDao()
    {
        server = new Server(this);
    }



    public void addListener(MessageReceivedListener listener)
    {
        listeners.add(listener);
    }

    public void removeListener(MessageReceivedListener listener)
    {
        listeners.remove(listener);
    }



    public void sendDifficulty(int difficulty)
    {
        //dataLock = true;
        server.setOutputBuffer(""+ difficulty);     // I know, I know, bad implicit string casting is bad
    }

    public void sendAnswer(String answer)
    {
        server.setOutputBuffer(answer);
    }



    public void onDataReceived(String data)
    {
        if (data.length() < 1) return;

        char messageType = data.charAt(0);
        switch (messageType)
        {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            {
                if (pendingQuestion == null)
                    pendingQuestion = new Question();
                String dataString = data.substring(1);

                // complete question's fields
                if (messageType == '0')
                    pendingQuestion.setStatement(dataString);
                else if (messageType == '1')
                    pendingQuestion.setAnswerA(dataString);
                else if (messageType == '2')
                    pendingQuestion.setAnswerB(dataString);
                else if (messageType == '3')
                    pendingQuestion.setAnswerC(dataString);
                else
                    pendingQuestion.setAnswerD(dataString);

                // once all fields are filled, send question to listeners
                if (pendingQuestion.isQuestionComplete())
                {
                    for (MessageReceivedListener l : listeners )
                    {
                        l.onQuestionReceived(pendingQuestion);
                    }
                    //pendingQuestion = null;
                }
                break;
            }
            case '5':
            case '6':
            {
                this.isCorrect = messageType == '5';
                for (MessageReceivedListener l : listeners)
                    l.onQuestionSuccess(messageType == '5');
                break;
            }
            case '7':
            {
                for (MessageReceivedListener l : listeners)
                    l.onGameSessionEnd();
                break;
            }
            case '8':
            {
                // load completed
                break;
            }
        }
    }

    public Question getPendingQuestion()
    {
        Question question = pendingQuestion;
        pendingQuestion = null;
        return question;
    }

    public Boolean getIsCorrect()
    {
        return isCorrect;
    }
}
