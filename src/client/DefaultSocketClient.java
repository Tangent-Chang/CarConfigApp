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

    private static final int READY = 0;
    private static final int UPLOADING = 1;
    private static final int DISPLAYING = 2;
    private static final int RETRIEVING = 3;
    private static final int CONFIGURING = 4;
    private static final int END = 5;
    private int MODE;

    public DefaultSocketClient(){
        setPort(PORT);
        setHost(ADDRESS);
    }

    public DefaultSocketClient(String strHost, int iPort) {
        setPort(iPort);
        setHost(strHost);
    }//constructor

    public DefaultSocketClient(ServerSocket serverSocket, Socket sock) {
        this.serverSocket = serverSocket;
        this.sock = sock;
    }//constructor

    public void run() {
        //while(true) {
            if (openConnection()) {
                handleSession();
                closeSession();
            }
        //}
    }//run

    public boolean openConnection(){
        try {
            sock = new Socket(strHost, iPort);
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
        displaySystemMessage("handle session");
        Scanner userInput = new Scanner(System.in);
        SelectCarOption clientSelect = new SelectCarOption();
        CarModelOptionsIO clientIO = new CarModelOptionsIO();

        try {
            while (true) {
                switch (MODE) {
                    case READY:
                        System.out.println("Enter command (upload, configure, exit): ");
                        String userCommand = userInput.nextLine().toLowerCase();
                        judgeMode(userCommand);
                        break;
                    case UPLOADING:
                        sendOutput("upload");
                        System.out.println("Enter file name: ");
                        String fileName = userInput.nextLine();
                        clientIO.uploadProperty(sock, fileName);
                        String confirm = reader.readLine();
                        displaySystemMessage("received server confirmation \"" + confirm + "\"");
                        MODE = READY;
                        break;
                    case DISPLAYING:
                        sendOutput("display");
                        ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                        ArrayList<String> modelList = (ArrayList<String>) ois.readObject();
                        for (String each : modelList) {
                            System.out.println(each);
                        }
                        MODE = RETRIEVING;
                        break;
                    case RETRIEVING:
                        sendOutput("retrieve");
                        System.out.println("Enter model name: ");
                        String selectedAuto = userInput.nextLine();
                        sendOutput(selectedAuto);
                        clientSelect.retrieveCar(sock);
                        displaySystemMessage("received car");
                        MODE = CONFIGURING;
                        break;
                    case CONFIGURING:
                        clientSelect.makeChoice();
                        break;
                    case END:
                        sendOutput("exit");
                        System.exit(0);
                        break;
                }
            }
        }catch (Exception e) {
            System.out.println("IOException :" + e.toString());
        }
    }

    public void sendOutput(String toServer){
        writer.println(toServer);
        displaySystemMessage("sent \"" + toServer + "\" to server");
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

    public void judgeMode(String userCommand){
        switch(userCommand){
            case "upload":
                MODE = UPLOADING;
                break;
            case "configure":
                MODE = DISPLAYING;
                break;
            case "exit":
                MODE = END;
                break;
            default:
                System.out.println("Invalid command");
                MODE = READY;
                break;

        }
    }
}

