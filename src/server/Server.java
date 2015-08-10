package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Tangent Chang on 7/8/15.
 */
public class Server implements Runnable{
    private ServerSocket serverSocket = null;
    private final int serverPort = 8765;  // 要監控的port

    public Server() {
        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Listening on port "+ serverPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + serverPort);
            System.exit(1);
        }
    }

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
    }

    /*public static void main (String arg[]){
        Server server = new Server();
        server.run();
    }*/
}
