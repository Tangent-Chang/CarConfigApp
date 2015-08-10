package client;



/**
 * Created by Tangent Chang on 7/14/15.
 */
public class Client implements Runnable{
    private String address = "127.0.0.1";
    private int port = 8765;
    //private Socket socketClient;
    //private InetSocketAddress isa;

    public void run(){
        DefaultSocketClient client;
        client = new DefaultSocketClient(this.address, this.port);
        client.start();
        System.out.println("client run in Client");
    }
    /*public static void main (String arg[]){
        Client client = new Client();
        client.run();
    }*/
}
