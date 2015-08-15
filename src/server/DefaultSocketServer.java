package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by Tangent Chang on 7/14/15.
 */
public class DefaultSocketServer extends Thread implements SocketClientInterface, SocketClientConstants {

    private BufferedReader reader;
    protected PrintWriter writer;
    private Socket sock;
    private ServerSocket serverSocket;
    private String strHost;
    private int iPort;
    IAutoServer autoServer = new BuildCarModelOptions();

    private static final int READY = 0;
    private static final int UPLOADING = 1;
    private static final int DISPLAYING = 2;
    private static final int RETRIEVING = 3;
    //private static final int CONFIGURING = 4;
    private static final int END = 5;
    private int MODE;

    public DefaultSocketServer(String strHost, int iPort) {
        setPort(iPort);
        setHost(strHost);
    }//constructor

    public DefaultSocketServer(ServerSocket serverSocket, Socket sock) {
        this.serverSocket = serverSocket;
        this.sock = sock;
    }//constructor

    public void run() {
        if (openConnection()) {
            handleSession();
            //closeSession();
        }
    }//run

    public boolean openConnection(){
        displaySystemMessage("open connection");
        try {
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new PrintWriter(sock.getOutputStream(), true);
            MODE = READY;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void handleSession(){
        displaySystemMessage("handle session");
        String clientCommand;
        ObjectOutputStream oos;
        try {
            while(true) {
                switch(MODE){
                    case READY:
                        displaySystemMessage("ready");
                        clientCommand = reader.readLine().toLowerCase();
                        judgeMode(clientCommand);
                        displaySystemMessage("client command is " + clientCommand);
                        break;
                    case UPLOADING:
                        displaySystemMessage("wait for file");
                        ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                        Properties propObj = (Properties) ois.readObject();
                        if (autoServer.buildWithProperty(propObj)) {
                            sendOutput("OK");
                        }
                        MODE = READY;
                        break;
                    case DISPLAYING:
                        oos = new ObjectOutputStream(sock.getOutputStream());
                        oos.writeObject(autoServer.getModelList());
                        displaySystemMessage("sent model list");
                        MODE = READY;
                        break;
                    case RETRIEVING:
                        String chosenAutoName = reader.readLine();
                        displaySystemMessage("received chosen model name, " + chosenAutoName);
                        oos = new ObjectOutputStream(sock.getOutputStream());
                        autoServer.sendSelectedAuto(oos, chosenAutoName);
                        displaySystemMessage("sent Auto object");
                        MODE = READY;
                        break;
                    case END:
                        System.exit(0);
                        break;
                }
            }
        }
        catch (EOFException eof) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        displaySystemMessage("handle session ends");
    }

    public void sendOutput(String toClient){
        writer.println(toClient);
        displaySystemMessage("sent \"" + toClient + "\" to client");
    }

    public void handleInput(String strInput){
        System.out.println(strInput);
    }

    public void closeSession(){
        try {
            writer = null;
            reader = null;
            sock.close();
            serverSocket.close();
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
        System.out.println(" [Server: " + message + "]");
    }

    public void judgeMode(String clientCommand){
        switch(clientCommand){
            case "upload":
                MODE = UPLOADING;
                break;
            case "display":
                MODE = DISPLAYING;
                break;
            case "retrieve":
                MODE = RETRIEVING;
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
