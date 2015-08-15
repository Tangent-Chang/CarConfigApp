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
    //private ObjectInputStream ois;
    IAutoServer autoServer = new BuildCarModelOptions();

    public DefaultSocketServer(String strHost, int iPort) {
        setPort(iPort);
        setHost(strHost);
    }//constructor

    public DefaultSocketServer(ServerSocket serverSocket, Socket sock) {
        this.serverSocket = serverSocket;
        this.sock = sock;
    }

    public void run() {
        if (openConnection()) {
            handleSession();
            System.out.println("server handle session finished");
            //closeSession();
        }

    }//run

    public boolean openConnection(){
        displaySystemMessage("open connection");
        try {
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new PrintWriter(sock.getOutputStream(), true);
            //ois = new ObjectInputStream(sock.getInputStream());

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
                //ois.skip(Long.MAX_VALUE);
                //String clientOption = (String) ois.readObject();
                displaySystemMessage("wait command");
                String clientOption = reader.readLine();
                displaySystemMessage("received client command, " + clientOption);

                if (clientOption.equals("upload")) {
                    displaySystemMessage("wait for file");
                    ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                    Properties propObj = (Properties) ois.readObject();
                    if (autoServer.buildWithProperty(propObj)) {
                        //ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                        //oos.flush();
                        //send confirmation message
                        //oos.writeObject("ok");
                        //System.out.println("Message sent to the client is " + "ok");
                        String confirm = "OK";
                        sendOutput(confirm);
                        displaySystemMessage("Message sent to the client is " + confirm);
                    }
                } else if (clientOption.equals("configure")) {
                    //send CarConfigApp_client.client CarConfigApp_client.CarConfigApp_server.model list
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(autoServer.getModelList());
                    //receive chosen auto name, then send auto obj
                    //ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                    //String chosenAutoName = (String) ois.readObject();
                    String chosenAutoName = reader.readLine();
                    displaySystemMessage("received chosen model name, " + chosenAutoName);
                    oos = new ObjectOutputStream(sock.getOutputStream());
                    autoServer.sendSelectedAuto(oos, chosenAutoName);
                    displaySystemMessage("sent Auto object");
                    //sock.shutdownOutput(); /* important */
                } else if (clientOption.equals("display")) {
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    oos.writeObject(autoServer.getModelList());
                    //System.out.println("server sent modelList");
                    displaySystemMessage("sent model list");
                } else if (clientOption.equals("select")) {
                    //System.out.println("server receive select");
                    //ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                    //String chosenAutoName = (String) ois.readObject();
                    String chosenAutoName = reader.readLine();
                    displaySystemMessage("received chosen model name, " + chosenAutoName);
                    ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
                    autoServer.sendSelectedAuto(oos, chosenAutoName);
                    displaySystemMessage("sent Auto object");
                } else if (clientOption.equals("exit")) {
                    System.exit(0);
                    //closeSession();
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
