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

    private BufferedReader reader;
    protected PrintWriter writer;
    protected Socket sock;
    private ServerSocket serverSocket;
    private String strHost;
    private int iPort;
    //protected ObjectOutputStream oos;
    //protected ObjectInputStream ois;

    private static final int WAITING = 0;
    private static final int UPLOADING = 1;
    private static final int DISPLAYING = 2;
    private static final int RETRIEVING = 3;
    private static final int CONFIGURING = 4;
    private static final int END = 5;

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
            //oos = new ObjectOutputStream(sock.getOutputStream());
            writer = new PrintWriter(sock.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            displaySystemMessage("open connection");
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
        displaySystemMessage("handle session");
        System.out.println("Enter function: ");
        userChoice = userInput.nextLine();

        try{
            //oos.writeObject(userChoice);
            sendOutput(userChoice);

            if(userChoice.equals("upload")){
                displaySystemMessage("uploading");
                CarModelOptionsIO clientIO = new CarModelOptionsIO();
                //ois = new ObjectInputStream(sock.getInputStream());
                System.out.println("Enter file name: ");
                String fileName = userInput.nextLine();
                clientIO.uploadProperty(sock, fileName);
                //ois = new ObjectInputStream(sock.getInputStream());
                //String confirmation = (String) ois.readObject();
                String confirm = reader.readLine();
                displaySystemMessage("received server confirmation \"" + confirm + "\"");
            }
            else if(userChoice.equals("configure")){
                SelectCarOption clientSelect = new SelectCarOption();

                displaySystemMessage("displaying");
                //clientSelect.showCars(sock, oos);
                ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                ArrayList<String> modelList = (ArrayList<String>) ois.readObject();
                for(String each : modelList){
                    System.out.println(each);
                }

                displaySystemMessage("retrieving");
                System.out.println("enter model name: ");
                String selectedAuto = userInput.nextLine();
                sendOutput(selectedAuto);
                clientSelect.retrieveCar(sock);
                displaySystemMessage("received car");
                clientSelect.makeChoice();
            }
            else if(userChoice.equals("exit")){
                sendOutput("exit");
                System.exit(0);
            }
            else{
                System.out.println("User choice is not valid!");
            }
        }
        catch(Exception e)
        {
            System.out.println("IOException :" + e.toString());
        }
    }

    public void sendOutput(String toServer){
        writer.println(toServer);
    }

    public void handleInput(String strInput){
        System.out.println(strInput);
    }

    public void closeSession(){
        try {
            writer = null;
            reader = null;
            sock.close();
            displaySystemMessage("session closed");
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

    public void displaySystemMessage(String message){
        System.out.println(" [Client: " + message + "]");
    }
}

