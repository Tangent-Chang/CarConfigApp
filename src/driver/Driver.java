package driver;


import client.DefaultSocketClient;

/**
 * Created by Tangent Chang on 6/14/15.
 */
public class Driver {
    public static void main(String [] args) {
        DefaultSocketClient client = new DefaultSocketClient();
        client.start();
    }
}
