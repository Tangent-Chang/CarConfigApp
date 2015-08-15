package client;


import model.Automobile;

import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Tangent Chang on 7/14/15.
 */
public class Client extends DefaultSocketClient{
    public static final String address = "127.0.0.1";
    public static final int port = 8765;
    private static Client clientInstance;
    //private Socket socketClient;
    //private InetSocketAddress isa;

    public Client() {
        super(address,port);
    }//constructor

    public static Client getInstance(){
        if(clientInstance == null){
            clientInstance = new Client();
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
    public static void main (String arg[]){
        //Client client = new Client();
        if(clientInstance == null){
            clientInstance = new Client();
        }
        clientInstance.start();
    }

    public ArrayList<String> getModelList(){
        ArrayList<String> modelList = null;
        try{
            //oos.writeObject("display");
            sendOutput("display");
            System.out.println("enter getModelList, and just sent server display");
            ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
            System.out.println("just new ois");
            modelList = (ArrayList<String>) ois.readObject();
            System.out.println("received model list");
        }
        catch(Exception e){
            System.out.println("IOException :" + e.toString());
        }
        return modelList;
    }
    public Automobile getAutoObj(String modelName){
        Automobile auto = null;
        try{
            //oos.writeObject("select");
            sendOutput("select");
            System.out.println("client sent select");
            //oos.writeObject(modelName);
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
