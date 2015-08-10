package client;

/**
 * Created by Tangent Chang on 8/4/15.
 */
public class ServletClient extends DefaultSocketClient{
    private static ServletClient instance;

    private ServletClient(String host, int port){
        super(host, port);
    }

    public static ServletClient getInstance() {
        if(instance ==null) {
            instance = new ServletClient("localhost",8765);
            instance.start();
            System.out.println("client run in ServletClient");
        }
        return instance;
    }

}
