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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void handleSession(){
        displaySystemMessage("handle session");
        try {
            while(true) {
                displaySystemMessage("wait command");
                String clientOption = reader.readLine();
                displaySystemMessage("received client command, " + clientOption);

                if (clientOption.equals("upload")) {
                    displaySystemMessage("wait for file");
                    ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                    Properties propObj = (Properties) ois.readObject();
                    if (autoServer.buildWithProperty(propObj)) {
                        String confirm = "OK";
                        sendOutput(confirm);
                        displaySystemMessage("Message sent to the client is " + confirm);
                    }
                } else if (clientOption.equals("configure")) {
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(autoServer.getModelList());
                    String chosenAutoName = reader.readLine();
                    displaySystemMessage("received chosen model name, " + chosenAutoName);
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    autoServer.sendSelectedAuto(oos, chosenAutoName);
                    displaySystemMessage("sent Auto object");
                } else if (clientOption.equals("display")) {
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(autoServer.getModelList());
                    displaySystemMessage("sent model list");
                } else if (clientOption.equals("select")) {
                    String chosenAutoName = reader.readLine();
                    displaySystemMessage("received chosen model name, " + chosenAutoName);
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    autoServer.sendSelectedAuto(oos, chosenAutoName);
                    displaySystemMessage("sent Auto object");
                } else if (clientOption.equals("exit")) {
                    System.exit(0);
                } else {
                    System.err.println("Invalid input from client!!!!!!!!!!");
                    //break;
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

}
