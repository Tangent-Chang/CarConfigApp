package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Tangent Chang on 7/8/15.
 */
public class Server extends DefaultSocketServer{
    //private ServerSocket serverSocket = null;
    public static final String serverHost = "127.0.0.1";
    private static final int serverPort = 8765;  // 要監控的port

    public Server() {
        super(serverHost, serverPort);
        /*try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Listening on port "+ serverPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + serverPort);
            System.exit(1);
        }*/
    }

    /*@Override
    public void run(){
        DefaultSocketServer server;
        try{
            while(true){
                server = new DefaultSocketServer(serverSocket, serverSocket.accept());
                server.start();
                System.out.println("server run in Server");
            }

        }
        catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }*/

    public static void main (String arg[]){
        Server server = new Server();
        server.start();
    }
}
