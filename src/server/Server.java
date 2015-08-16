package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Tangent Chang on 7/8/15.
 */
public class Server implements SocketClientConstants{
    private ServerSocket serverSocket = null;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println(" [Server: Listening on port "+ PORT + "]");
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT);
            System.exit(1);
        }
    }

    public void run(){
        DefaultSocketServer server;
        try{
            while(true){
                server = new DefaultSocketServer(serverSocket, serverSocket.accept());
                server.start();
                System.out.println(" [Server: started]");
            }
        }
        catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }

    public static void main (String arg[]){
        Server server = new Server();
        server.run();
    }
}
