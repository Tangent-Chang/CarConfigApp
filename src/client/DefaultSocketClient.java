package client;

import server.SocketClientConstants;
import server.SocketClientInterface;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Tangent Chang on 7/8/15.
 */
public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {

    //private BufferedReader reader;
    //private PrintWriter writer;
    protected Socket sock;
    private ServerSocket serverSocket;
    private String strHost;
    private int iPort;
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;

    public DefaultSocketClient(String strHost, int iPort) {
        setPort(iPort);
        setHost(strHost);
    }//constructor

    public DefaultSocketClient(ServerSocket serverSocket, Socket sock) {
        this.serverSocket = serverSocket;
        this.sock = sock;
    }

    public void run() {
        while(true) {
            if (openConnection()) {
                handleSession();
                closeSession();
            }
        }
    }//run

    public boolean openConnection(){
        try {
            sock = new Socket(strHost, iPort);
            oos = new ObjectOutputStream(sock.getOutputStream());
            System.out.println("oos is not null");

            /*ois = new ObjectInputStream(sock.getInputStream());
            System.out.println("already new ois in client");*/
        }
        catch(IOException socketError){
            if (DEBUG) System.err.println
                    ("Unable to connect to " + strHost);
            return false;
        }
        return true;
    }

    public void handleSession(){
        Scanner userInput = new Scanner(System.in);
        String userChoice;
        System.out.println("enter function: ");
        userChoice = userInput.nextLine();

        try{
            oos.writeObject(userChoice);

            if(userChoice.equals("upload")){
                CarModelOptionsIO clientIO = new CarModelOptionsIO();
                clientIO.uploadProperty(sock);
            }
            else if(userChoice.equals("configure")){
                SelectCarOption clientSelect = new SelectCarOption();
                clientSelect.configureCar(sock, oos);
            }
            else if(userChoice.equals("exit")){
                //System.exit(0);
                closeSession();
            }
            else{
                System.out.println("User choice is not valid!");
            }

        }
        catch(Exception e)
        {
            //System.out.println("Socket連線有問題 !" );
            System.out.println("IOException :" + e.toString());
        }
    }

    public void sendOutput(String strOutput){
        /*try {
            writer.write(strOutput, 0, strOutput.length());
        }
        catch (IOException e){
            if (DEBUG) System.out.println
                    ("Error writing to " + strHost);
        }*/
    }

    public void handleInput(String strInput){
        System.out.println(strInput);
    }

    public void closeSession(){
        try {
            sock.close();
        }
        catch (IOException e){
            if (DEBUG) System.err.println
                    ("Error closing socket to " + strHost);
        }
    }

    public void setHost(String strHost){
        this.strHost = strHost;
    }

    public void setPort(int iPort){
        this.iPort = iPort;
    }
}

