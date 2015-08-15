package client;


import model.Automobile;

import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Tangent Chang on 7/14/15.
 */
public class ServletClient extends DefaultSocketClient{
    //public static final String address = "127.0.0.1";
    //public static final int port = 8765;
    private static ServletClient clientInstance;

    public ServletClient() {
        super(ADDRESS,PORT);
    }//constructor

    public static ServletClient getInstance(){
        if(clientInstance == null){
            clientInstance = new ServletClient();
            clientInstance.openConnection();
        }
        return clientInstance;
    }

    /*@Override
    public void run(){
        while(true) {
            if (openConnection()) {
                System.out.println("client already open connection, will handle session");
                handleSession();
                closeSession();
            }
        }
    }*/
    /*public static void main (String arg[]){
        if(clientInstance == null){
            clientInstance = new ServletClient();
        }
        clientInstance.start();
    }*/

    public ArrayList<String> getModelList(){
        ArrayList<String> modelList = null;
        try{
            sendOutput("display");
            ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
            modelList = (ArrayList<String>) ois.readObject();
        }
        catch(Exception e){
            System.out.println("IOException :" + e.toString());
        }
        return modelList;
    }
    public Automobile getAutoObj(String modelName){
        Automobile auto = null;
        try{
            sendOutput("retrieve");
            sendOutput(modelName);
            ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
            auto  = (Automobile) ois.readObject();
        }
        catch(Exception e){
            System.out.println("IOException :" + e.toString());
        }
        return auto;
    }
}
