package ca.usherbrooke.www.vrohms.Data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by William on 11/9/2017.
 */
public class Server
{
    //Receive port: 9002
    //Send port: 1234
    private RobotDao parent;
    private ServerSocket serverSocket;
    static final int socketServerPORT = 1234;
    private boolean end = false;

    private String outputBuffer = null;

    public Server(RobotDao parent)
    {
        this.parent = parent;
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
        startServerSocket();
        Log.d("TEST", "new Server()");
    }

    private void startServerSocket()
    {
        Thread thread = new Thread(new Runnable()
        {
            private String dataString = null;

            @Override
            public void run()
            {
                try
                {
                    ServerSocket ss = new ServerSocket(9002);
                    Log.v("TEST", "new ServerSocket()");

                    while (!end)
                    {
                        //Server is waiting for client here, if needed
                        Socket s = ss.accept();
                        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter output = new PrintWriter(s.getOutputStream());

                        dataString = input.readLine();
                        output.println("FROM SERVER - " + dataString.toUpperCase());
                        output.flush();

                        try
                        {
                            Thread.sleep(50);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                        //Handles the received string

                        parent.onDataReceived(dataString);

                        if (dataString.equalsIgnoreCase("STOP"))
                        {
                            end = true;
                            output.close();
                            s.close();
                            break;
                        }

                        output.close();
                        s.close();
                    }
                    ss.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }

    public void setOutputBuffer(String outputBuffer)
    {
        this.outputBuffer = outputBuffer;
    }

//    public void onDestroy()
//    {
//        if (serverSocket != null)
//        {
//            try
//            {
//                serverSocket.close();
//            }
//            catch (IOException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }



    private class SocketServerThread extends Thread
    {
        int count = 0;

        @Override
        public void run()
        {
            try
            {
                // create ServerSocket using specified port
                serverSocket = new ServerSocket(socketServerPORT);
                //InputStream inStream;

                while (true)
                {
                    // block the call until connection is created and return
                    // Socket object
                    Socket socket = serverSocket.accept();

                    SocketServerReplyThread socketServerReplyThread =
                            new SocketServerReplyThread(socket, ++count);
                    socketServerReplyThread.run();

                }
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class SocketServerReplyThread extends Thread
    {
        private Socket hostThreadSocket;
        int cnt;

        public SocketServerReplyThread(Socket socket, int c)
        {
            hostThreadSocket = socket;
            cnt = c;
        }

        @Override
        public void run()
        {
            OutputStream outputStream;

            try
            {
                while (outputBuffer == null);

                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(outputBuffer);
                printStream.close();

                outputBuffer = null;
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //message += "Something wrong! " + e.toString() + "\n";
            }
        }

    }

}

